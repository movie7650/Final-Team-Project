package com.example.daitso.customercoupon.service;

import java.util.ArrayList;
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
	
	//사용가능한 쿠폰목록 조회
	@Override
	public List<SelectCustomerCoupon> selectUsableCoupon(int customerId, int page) {
		int start = (page-1)*10 + 1;
		return customerCouponRepository.selectUsableCoupon(customerId,start, start+9);
	}
	//사용불가능한 쿠폰 목록 출력
	@Override
	public List<SelectCustomerCoupon> selectBanCoupon(int customerId, int page) {
		int start = (page-1)*10 + 1;
		return customerCouponRepository.selectBanCoupon(customerId, start, start+9);
	}
	
	@Override
	public void insertCustomerCoupon(String customerId, String allCouponNum) {
		customerCouponRepository.insertCustomerCoupon(customerId, allCouponNum);
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
		
	//존재하는 쿠폰 카운트
	@Override
	public int countExistCouponSn(String customerId, String allCouponNum) {
		return customerCouponRepository.countExistCouponSn(customerId,allCouponNum);
	}

	@Override
	public int countExistCouponId(String allCouponNum) {
		return customerCouponRepository.countExistCouponId(allCouponNum);
	}
	//사용가능한 쿠폰 갯수 count
	@Override
	public int countUsableCustomerCoupon(int customerId) {
		return customerCouponRepository.countUsableCustomerCoupon(customerId);
	}
	//사용자 사용불가능한 쿠폰 갯수 count
	@Override
	public int countCantUseCustomerCoupon(int customerId) {
		return customerCouponRepository.countCantUseCustomerCoupon(customerId);
	}
}
