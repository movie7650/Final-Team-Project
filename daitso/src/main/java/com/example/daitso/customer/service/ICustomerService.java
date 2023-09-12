package com.example.daitso.customer.service;

import java.util.Optional;

import com.example.daitso.customer.model.CustomerName;
import com.example.daitso.customer.model.CustomerSignUp;

public interface ICustomerService {
	
	// 이메일 중복체크
	Optional<Integer> checkEmailDuplicated(String customerEmail);
	
	// 휴대폰 중복체크
	Optional<Integer>  checkTelnoDuplicated(String customerTelno);
	
	// 회원가입
	void insertIntoCustomer(CustomerSignUp customerSignUp);
	
	// 사용자 고유번호로부터 사용자 이름 갖고오기
	CustomerName getCustomerNmByCustomerId(int customerId);
	
	public String selectCustomerEmail();
}
