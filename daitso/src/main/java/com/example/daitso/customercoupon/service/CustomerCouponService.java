package com.example.daitso.customercoupon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.customercoupon.model.SelectCustomerCoupon;
import com.example.daitso.customercoupon.repository.ICustomerCouponRepository;

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

}
