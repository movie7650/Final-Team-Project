package com.example.daitso.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.daitso.oauth.model.OAuth2UserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int customerId = -1;
		
		// 소셜 로그인,내부 로그인 구분
		if("OAuth2UserDetails".equals(principal.getClass().getSimpleName())) {
			OAuth2UserDetails OAuth2UserDetails = (OAuth2UserDetails) principal;
			customerId = OAuth2UserDetails.getCustomerId();
		} else if("User".equals(principal.getClass().getSimpleName())) {
			UserDetails userDetails = (UserDetails) principal;
			customerId = Integer.valueOf(userDetails.getUsername());
		} 
		
		if(customerId == 0) {
			return true;
		} else {
			response.sendRedirect("/customer/login");
			return false;
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("error");
	}
	
}
