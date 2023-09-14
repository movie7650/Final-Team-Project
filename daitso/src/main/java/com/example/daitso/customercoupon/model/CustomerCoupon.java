package com.example.daitso.customercoupon.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerCoupon {
	private int customerCouponId;
	private int customerId;
	private int couponId;
	private int couponUseDv;
	private String couponUseDt;
	private char status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
}
