package com.example.daitso.coupon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.coupon.model.Coupon;
import com.example.daitso.coupon.model.CouponEvent;
import com.example.daitso.coupon.model.CouponEventInsert;
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

	@Transactional
	public int insertEventCoupon(CouponEventInsert coupon) {
		validateDuplicateCoupon(coupon);
		return 	couponRepository.insertEventCoupon(coupon.getCustomerId(), coupon.getCouponId());
	}
	
	private void validateDuplicateCoupon(CouponEventInsert coupon) {
		int num = couponRepository.getEventCouponCheck(coupon.getCustomerId(), coupon.getCouponId());
		if(num > 0) throw new IllegalStateException("이미 존재하는 쿠폰입니다.");
	}

}
