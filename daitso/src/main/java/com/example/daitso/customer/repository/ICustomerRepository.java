package com.example.daitso.customer.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.customer.model.CustomerName;
import com.example.daitso.customer.model.CustomerSecurity;
import com.example.daitso.customer.model.CustomerSignUp;

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
	
	//사용자 아이디(이메일) 가져오기
	public String selectCustomerEmail();
}
