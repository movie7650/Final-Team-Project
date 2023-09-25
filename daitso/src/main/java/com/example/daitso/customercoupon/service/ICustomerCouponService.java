package com.example.daitso.customercoupon.service;

import java.util.List;

import com.example.daitso.customercoupon.model.SelectCustomerCoupon;
import com.example.daitso.cart.model.CartCouponApply;

public interface ICustomerCouponService {
	
	//사용가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectUsableCoupon(int customerId);
	//사용불가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectBanCoupon(int customerId);
	//사용자 쿠폰등록
	void insertCustomerCoupon(String customerId,String allCouponNum);
	// 쿠폰 유효기간 지났는지 확인 -> 지나면 만료로 바꾸기
	void checkCouponEprDt();
	
	// 적용가능한 쿠폰 조회
	List<CartCouponApply> getCouponsByCustomerId(int categoryId, int customerId);
	
}
