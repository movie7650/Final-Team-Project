package com.example.daitso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.daitso.filter.LoginInterceptor;
import com.example.daitso.filter.XSSFilter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public XSSFilter xssFilter() {
		return new XSSFilter();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor())
				.addPathPatterns("/cart/**");
	}
}
