package com.example.daitso.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.customer.model.CustomerInfo;
import com.example.daitso.customer.model.CustomerName;
import com.example.daitso.customer.model.CustomerSignUp;
import com.example.daitso.customer.repository.ICustomerRepository;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	ICustomerRepository customerRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 이메일 중복체크
	@Override
	public Optional<Integer> checkEmailDuplicated(String customerEmail) {
		return customerRepository.checkEmailDuplicated(customerEmail);
	}
	
	// 휴대폰 중복체크
	@Override
	public Optional<Integer> checkTelnoDuplicated(String customerTelno) {
		return customerRepository.checkTelnoDuplicated(customerTelno);
	}

	// 회원가입
	@Transactional
	public void insertIntoCustomer(CustomerSignUp customerSignUp){
		
		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(customerSignUp.getCustomerPw());
		customerSignUp.setCustomerPw(encryptedPassword);
		
		customerRepository.insertIntoCustomer(customerSignUp);
	}

	// 사용자 고유번호로부터 사용자 이름 갖고오기
	@Override
	public CustomerName getCustomerNmByCustomerId(int customerId) {
		return customerRepository.getCustomerNmByCustomerId(customerId);
	}

	// 사용자 고유번호로부터 사용자 정보(이름,이메일,휴대폰번호) 갖고오기
	@Override
	public CustomerInfo getCustomerInfoByCustomerId(int customerId) {
		return customerRepository.getCustomerInfoByCustomerId(customerId);
	}
	
	//사용자 이메일 가져오기 
	@Override
	public String selectCustomerEmail() {
		return customerRepository.selectCustomerEmail();
	}

	// 새 비밀번호 설정
	@Transactional
	public void settingPassword(String password, String email) {
		String encryptedPassword = passwordEncoder.encode(password);
		customerRepository.settingPassword(encryptedPassword, email);
	}

	// 사용자 휴대폰번호로부터 이메일 조회
	@Override
	public String getCustomerEmailByCustomerTelno(String customerTelno) {
		return customerRepository.getCustomerEmailByCustomerTelno(customerTelno);
	}	
}
