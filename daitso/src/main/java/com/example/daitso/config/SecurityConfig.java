package com.example.daitso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.daitso.oauth.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
		
	@Autowired
	CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            	.csrf((csrf) -> csrf
            		.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            	.formLogin((formLogin) -> formLogin
                        .loginPage("/customer/login")
                        .successHandler(myAuthenticationSuccessHandler()))
            	.logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/customer/logout"))
                        .logoutSuccessUrl("/customer/login")
                        .invalidateHttpSession(true))
            	.oauth2Login((oauth2Login) -> oauth2Login
            			.userInfoEndpoint((userInfo) -> userInfo
            				.userService(customOAuth2UserService)
            			)
            		);
        return http.build();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	// 관리자 사용자 로그인 성공시 화면 다르게!
	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
	    return new MySimpleUrlAuthenticationSuccessHandler();
	}
	
}