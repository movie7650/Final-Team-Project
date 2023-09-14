package com.example.daitso.customercoupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.cart.model.CartCouponApply;
import com.example.daitso.customercoupon.repository.ICustomerCouponRepository;

@Controller
@RequestMapping("/customer-coupon")
public class CustomerCouponController {
	
	@Autowired
	ICustomerCouponRepository customerCouponRepository;
	
	// 적용 가능한 쿠폰 조회
	@GetMapping("/coupon-apply/{customerId}/{categoryId}")
	public @ResponseBody List<CartCouponApply> getCartCouponApply(@PathVariable int customerId, @PathVariable int categoryId){
		return customerCouponRepository.getCouponsByCustomerId(categoryId, customerId);
	}
}
