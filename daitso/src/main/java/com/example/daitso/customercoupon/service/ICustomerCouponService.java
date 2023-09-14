package com.example.daitso.customercoupon.service;

import java.util.List;

import com.example.daitso.customercoupon.model.SelectCustomerCoupon;

public interface ICustomerCouponService {
	//사용가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectUsableCoupon();
	//사용불가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectBanCoupon();
	//사용자 쿠폰등록
	void insertCustomerCoupon();


}
