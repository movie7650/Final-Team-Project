package com.example.daitso.coupon.service;

import java.util.List;

import com.example.daitso.coupon.model.Coupon;
import com.example.daitso.coupon.model.CouponEvent;
import com.example.daitso.coupon.model.CouponEventInsert;

public interface ICouponService {
	
	//이벤트 쿠폰 조회하기
	List<CouponEvent> selectEventCoupon(int page);
	
	//이벤트 쿠폰 개수 조회하기
	int countEventCoupon();
	
	//이벤트 쿠폰 다운로드
	int insertEventCoupon(CouponEventInsert coupon);
}
