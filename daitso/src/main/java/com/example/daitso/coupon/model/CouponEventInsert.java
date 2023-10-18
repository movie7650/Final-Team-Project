package com.example.daitso.coupon.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CouponEventInsert {
	private int couponId;
	private int customerId;
}
