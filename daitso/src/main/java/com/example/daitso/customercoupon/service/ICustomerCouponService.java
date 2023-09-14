package com.example.daitso.customercoupon.service;

import java.util.List;

import com.example.daitso.cart.model.CartCouponApply;

public interface ICustomerCouponService {
	
	// 쿠폰 유효기간 지났는지 확인 -> 지나면 만료로 바꾸기
	void checkCouponEprDt();
	
	// 적용가능한 쿠폰 조회
	List<CartCouponApply> getCouponsByCustomerId(int categoryId, int customerId);
}
