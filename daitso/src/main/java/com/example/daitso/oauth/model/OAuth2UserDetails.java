package com.example.daitso.oauth.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class OAuth2UserDetails extends DefaultOAuth2User{

	private int customerId;
	
	public OAuth2UserDetails(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
			String nameAttributeKey, int customerId) {
		super(authorities, attributes, nameAttributeKey);
		this.customerId = customerId;
	}
}
