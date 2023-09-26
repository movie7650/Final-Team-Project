package com.example.daitso.oauth.model;

import java.util.Map;

import com.example.daitso.customer.model.CustomerSocial;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OAuthAttributes {
	private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String telno;
    
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("google".equals(registrationId)) {
        	return ofGoogle(userNameAttributeName, attributes);
        } else if("naver".equals(registrationId)) {
        	return ofNaver(userNameAttributeName, attributes);
        }
        
        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
    	@SuppressWarnings("unchecked")
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .telno((String) response.get("mobile"))
                .attributes(response)
                .nameAttributeKey("id")
                .build();
    }
    
    public CustomerSocial toEntity() {
        return CustomerSocial.builder()
                .customerNm(name)
                .customerEmail(email)
                .customerTelno(telno)
                .build();
    }
}
