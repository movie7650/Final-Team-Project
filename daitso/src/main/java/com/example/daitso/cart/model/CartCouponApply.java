package com.example.daitso.cart.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCouponApply {
	private int customerCouponId;
	private int couponId;
	private String couponNm;
	private float couponDscntRate;
}
