package com.example.daitso.coupon.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CouponEvent {
	private int couponId;
	private int categoryId; 
	private String couponNm; 
	private int couponDscntRate;
	private Date couponPblDt;
	private Date couponEprDt;
}
