package com.example.daitso.oauth.service;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.customer.model.CustomerRole;
import com.example.daitso.customer.model.CustomerSecurity;
import com.example.daitso.customer.model.CustomerSignUpWithSocial;
import com.example.daitso.customer.repository.ICustomerRepository;
import com.example.daitso.oauth.model.OAuth2UserDetails;
import com.example.daitso.oauth.model.OAuthAttributes;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

	@Autowired
	ICustomerRepository customerRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		int customerId = 0;
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
             
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        
        Optional<CustomerSecurity> user = customerRepository.findByCustomerEmailWithSocial(attributes.getEmail(), registrationId);
        if(user.isEmpty()) {
        	CustomerSignUpWithSocial customerSignUpWithSocial = CustomerSignUpWithSocial.builder()	
        																				.customerEmail(attributes.getEmail())
        																				.customerNm(attributes.getName())
        																				.customerPw("1234")
        																				.loginMethod(registrationId).build();
        	customerRepository.insertIntoCustomerWithSocial(customerSignUpWithSocial);
        	customerId = customerRepository.findCustomerIdByCustomerEmailAndLoginMethod(attributes.getEmail(),registrationId);	
        } else {
        	customerId = user.get().getCustomerId();
        }
        
        return new OAuth2UserDetails(Collections.singleton(new SimpleGrantedAuthority(CustomerRole.USER.getValue())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey(),
                customerId);
	}	
}
