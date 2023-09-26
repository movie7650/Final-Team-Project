package com.example.daitso.check;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.daitso.oauth.model.OAuth2UserDetails;

@Service
public class LoginCheckService implements ILogincheckService{

	// spring security -> 사용자 고유번호 받아오기
	@Override
	public int loginCheck() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int customerId = 0;
		
		// 소셜 로그인,내부 로그인 구분
		if("OAuth2UserDetails".equals(principal.getClass().getSimpleName())) {
			OAuth2UserDetails OAuth2UserDetails = (OAuth2UserDetails) principal;
			customerId = OAuth2UserDetails.getCustomerId();
		} else if("User".equals(principal.getClass().getSimpleName())) {
			UserDetails userDetails = (UserDetails) principal;
			customerId = Integer.valueOf(userDetails.getUsername());
		} else {
			return -1;
		}
		return customerId;
	}
	
}
