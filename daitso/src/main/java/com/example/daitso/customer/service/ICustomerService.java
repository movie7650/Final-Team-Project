package com.example.daitso.customer.service;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.example.daitso.customer.model.CheckMyInform;
import com.example.daitso.customer.model.CustomerInfo;
import com.example.daitso.customer.model.CustomerName;
import com.example.daitso.customer.model.CustomerSignUp;

public interface ICustomerService {

	// 이메일 중복체크
	Optional<Integer> checkEmailDuplicated(String customerEmail);

	// 휴대폰 중복체크
	Optional<Integer> checkTelnoDuplicated(String customerTelno);

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

	// 내정보 SELECT-------------------------------------------------------------
	// 내정보조회
	List<CheckMyInform> selectMyInform(int customerId);

	// 내 아이디(이메일) 가져오기
	String selectMyEmail(int customerId);

	// 내 이름 가져오기
	String selectMyName(int customerId);

	// 내 휴대폰 번호 가져오기
	String selectMyTelNo(int customerId);

	// 내비밀번호 가져오기
	String selectMyPassword(int customerId);

	// 내정보
	// UPDATE--------------------------------------------------------------------------
	// 내 이름 변경하기
	void updateMyName(@Param("customerId") int customerId, @Param("newName") String newName);

	// 내 이메일 변경하기
	void updateMyEmail(@Param("customerId") int customerId, @Param("newEmail") String newEmail);

	// 내 전화번호 변경하기
	void updateMyTelNO(@Param("customerId") int customerId, @Param("newTelNO") String newTelNO);
	
	//내 비밀번호 변경하기
	void updateMyPassword(@Param("customerId") int customerId, @Param("newPassword") String newPassword);

}
