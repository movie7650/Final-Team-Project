package com.example.daitso.customercoupon.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;


//사용가능 및 사용불가능 쿠폰출력 DTO
@Getter @Setter
public class SelectCustomerCoupon {
	private String couponNm;
	private double couponDscntRate;
	private Date couponEprDt;
	private int couponUseDv;

}
