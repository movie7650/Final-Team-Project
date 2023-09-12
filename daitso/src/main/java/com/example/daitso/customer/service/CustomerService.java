package com.example.daitso.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public CustomerName getCustomerNmByCustomerId(int customerId) {
		return customerRepository.getCustomerNmByCustomerId(customerId);
	}
	
	//사용자 이메일 가져오기 
	@Override
	public String selectCustomerEmail() {
		// TODO Auto-generated method stub
		return customerRepository.selectCustomerEmail();
	}	
}
