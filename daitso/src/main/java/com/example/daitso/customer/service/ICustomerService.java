package com.example.daitso.customer.service;

import java.util.List;
import java.util.Optional;

import com.example.daitso.customer.model.CheckMyInform;
import com.example.daitso.customer.model.CustomerInfo;
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
	

	// 사용자 고유번호로부터 사용자 정보(이름,이메일,휴대폰번호) 갖고오기
	CustomerInfo getCustomerInfoByCustomerId(int customerId);

	// 사용자 이메일 가져오기 
	public String selectCustomerEmail();

	// 새 비밀번호 설정
	void settingPassword(String password, String email);
    
	// 사용자 휴대폰번호로부터 이메일 조회
	String getCustomerEmailByCustomerTelno(String customerTelno);
	
	//내정보조회 
	List<CheckMyInform> selectMyInform(int customerId);
	
	//내비밀번호 가져오기
	String selectMyPassword(int customerId);

}
