package com.example.daitso.coupon.service;

import java.util.List;

import com.example.daitso.coupon.model.Coupon;
import com.example.daitso.coupon.model.CouponEvent;

public interface ICouponService {
	
	//이벤트 쿠폰 조회하기
	List<CouponEvent> selectEventCoupon(int page);
	
	//이벤트 쿠폰 개수 조회하기
	int countEventCoupon();
}
