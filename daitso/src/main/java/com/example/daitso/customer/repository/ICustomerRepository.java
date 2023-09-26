package com.example.daitso.customer.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.customer.model.CustomerInfo;
import com.example.daitso.customer.model.CustomerName;
import com.example.daitso.customer.model.CustomerSecurity;
import com.example.daitso.customer.model.CustomerSignUp;
import com.example.daitso.customer.model.CustomerSignUpWithSocial;

@Repository
@Mapper
public interface ICustomerRepository {
	
	// 이메일 중복체크
	Optional<Integer> checkEmailDuplicated(String customerEmail);
	
	// 휴대폰번호 중복체크
	Optional<Integer> checkTelnoDuplicated(String customerTelno);
	
	// 회원가입
	void insertIntoCustomer(CustomerSignUp customerSignUp);
	
	// 로그인
	Optional<CustomerSecurity> findByCustomerEmail(String customerEmail);
	
	// 사용자 고유번호로부터 사용자 이름 갖고오기
	CustomerName getCustomerNmByCustomerId(int customerId);
	
	// 사용자 고유번호로부터 사용자 정보(이름,이메일,휴대폰번호) 갖고오기
	CustomerInfo getCustomerInfoByCustomerId(int customerId);
	
	//사용자 아이디(이메일) 가져오기
	public String selectCustomerEmail();

	// 새 비밀번호 설정
	void settingPassword(@Param("password") String password, @Param("email") String email);

	// 사용자 휴대폰번호로부터 이메일 조회
	String getCustomerEmailByCustomerTelno(String customerTelno);
	
	// 회원가입(소셜 로그인)
	void insertIntoCustomerWithSocial(CustomerSignUpWithSocial customerSignUpWithSocial);

	// 이메일과 로그인 방식으로 사용자 고유번호 갖고오기
	int findCustomerIdByCustomerEmailAndLoginMethod(@Param("customerEmail") String customerEmail, @Param("loginMethod") String loginMethod);

	// 소셜 로그인 아이디 중복확인
	Optional<CustomerSecurity> findByCustomerEmailWithSocial(@Param("customerEmail") String customerEmail, @Param("loginMethod") String loginMethod);

}
