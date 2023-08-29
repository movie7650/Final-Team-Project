package com.example.daitso.customer.service;

import java.util.Optional;

import com.example.daitso.customer.model.CustomerSignUp;

public interface ICustomerService {
	
	// 이메일 중복체크
	Optional<Integer> checkEmailDuplicated(String customerEmail);
	
	// 휴대폰 중복체크
	Optional<Integer>  checkTelnoDuplicated(String customerTelno);
	
	// 회원가입
	void insertIntoCustomer(CustomerSignUp customerSignUp);
}
