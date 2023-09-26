package com.example.daitso.coupon.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CouponCheck {
	private int couponId;
	private int categoryId;
	private String couponSn;
	private String couponNm;
	private int couponDscntRate;
	private String couponPblDt;
	private String couponEprDt;
	private char status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
	private int couponTerm;
	
	private int rn;
}
