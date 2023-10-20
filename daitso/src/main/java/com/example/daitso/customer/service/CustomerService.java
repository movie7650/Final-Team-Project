package com.example.daitso.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.customer.model.CheckMyInform;
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
	public void insertIntoCustomer(CustomerSignUp customerSignUp) {
		
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

	// 사용자 이메일 가져오기
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

	// 내 정보 조회
	@Override
	public List<CheckMyInform> selectMyInform(int customerId) {
		return customerRepository.selectMyInform(customerId);
	}

	// 내 비밀번호가져오기
	@Override
	public String selectMyPassword(int customerId) {
		return customerRepository.selectMyPassword(customerId);
	}

	// 내 아이디(이메일) 가져오기
	@Override
	public String selectMyEmail(int customerId) {
		return customerRepository.selectMyEmail(customerId);
	}

	// 내 이름 가져오기
	@Override
	public String selectMyName(int customerId) {
		return customerRepository.selectMyName(customerId);
	}

	// 내 전화번호 가져오기
	@Override
	public String selectMyTelNo(int customerId) {
		return customerRepository.selectMyTelNo(customerId);
	}

	// 내이름 변경하기
	@Override
	public void updateMyName(int customerId, String newName) {
		customerRepository.updateMyName(customerId, newName);
	}

	// 내 아이디(이메일) 변경하기
	@Override
	public void updateMyEmail(int customerId, String newEmail) {
		customerRepository.updateMyEmail(customerId, newEmail);
	}

	// 내 전화번호 변경하기
	@Override
	public void updateMyTelNO(int customerId, String newTelNO) {
		customerRepository.updateMyTelNO(customerId, newTelNO);
	}

	@Override
	public void updateMyPassword(int customerId, String newPassword) {
		customerRepository.updateMyPassword(customerId, newPassword);
		
	}
	//새로 입력된 이메일의 중복체크를 위한 카운트
	@Override
	public int countNewEmail(String newEmail) {
		return customerRepository.countNewEmail(newEmail);
	}
}
