package com.example.daitso.customercoupon.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.customercoupon.model.SelectCustomerCoupon;
import com.example.daitso.customercoupon.repository.ICustomerCouponRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.cart.model.CartCouponApply;


@Service
public class CustomerCouponService implements ICustomerCouponService {
	
	@Autowired
	ICustomerCouponRepository customerCouponRepository;
	
	@Override
	public List<SelectCustomerCoupon> selectUsableCoupon() {
		return customerCouponRepository.selectUsableCoupon();
	}
	
	@Override
	public List<SelectCustomerCoupon> selectBanCoupon() {
		return customerCouponRepository.selectBanCoupon();
	}
	
	@Override
	public void insertCustomerCoupon() {
		customerCouponRepository.insertCustomerCoupon();
	}
	
	// 매자정마다 쿠폰 유효기간 지났는지 확인 -> 지나면 만료로 바꾸기
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void checkCouponEprDt() {
		customerCouponRepository.checkCouponEprDt();
	}
	
	// 적용가능한 쿠폰 조회
	@Override
	public List<CartCouponApply> getCouponsByCustomerId(int categoryId, int customerId) {
		return customerCouponRepository.getCouponsByCustomerId(categoryId, customerId);
	}
}
