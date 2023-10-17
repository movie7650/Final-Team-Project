package com.example.daitso.coupon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.coupon.model.Coupon;
import com.example.daitso.coupon.model.CouponEvent;
import com.example.daitso.coupon.repository.ICouponRepository;

@Service
public class CouponService implements ICouponService {

	@Autowired
	ICouponRepository couponRepository;
	
	@Override
	public List<CouponEvent> selectEventCoupon(int page) {
		int start = 9 * (page-1) + 1; 
		return couponRepository.selectEventCoupon(start, start+8);
	}

	@Override
	public int countEventCoupon() {
		return couponRepository.countEventCoupon();
	}

}
