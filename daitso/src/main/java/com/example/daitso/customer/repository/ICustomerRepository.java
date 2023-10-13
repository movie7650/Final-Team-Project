package com.example.daitso.customer.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.customer.model.CheckMyInform;
import com.example.daitso.customer.model.CustomerChart;
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

	// 사용자 아이디(이메일) 가져오기
	public String selectCustomerEmail();

	// 새 비밀번호 설정
	void settingPassword(@Param("password") String password, @Param("email") String email);

	// 사용자 휴대폰번호로부터 이메일 조회
	String getCustomerEmailByCustomerTelno(String customerTelno);

	// 내정보 SELECT
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

	// 정보 UPDATE
	// 내 이름 변경하기
	void updateMyName(@Param("customerId") int customerId, @Param("newName") String newName);

	// 내 이메일 변경하기
	void updateMyEmail(@Param("customerId") int customerId, @Param("newEmail") String newEmail);

	// 내 전화번호 변경하기
	void updateMyTelNO(@Param("customerId") int customerId, @Param("newTelNO") String newTelNO);
	
	//내 비밀번호 변경하기
	void updateMyPassword(@Param("customerId") int customerId, @Param("newPassword") String newPassword);

	// 회원가입(소셜 로그인)
	void insertIntoCustomerWithSocial(CustomerSignUpWithSocial customerSignUpWithSocial);

	// 이메일과 로그인 방식으로 사용자 고유번호 갖고오기
	int findCustomerIdByCustomerEmailAndLoginMethod(@Param("customerEmail") String customerEmail,
			@Param("loginMethod") String loginMethod);

	// 소셜 로그인 아이디 중복확인
	Optional<CustomerSecurity> findByCustomerEmailWithSocial(@Param("customerEmail") String customerEmail,
			@Param("loginMethod") String loginMethod);

	// 현재 시간을 기준으로 5개월 동안의 월별 회원가입 수 조회하기
	List<CustomerChart> getCustomerCounts();
}
