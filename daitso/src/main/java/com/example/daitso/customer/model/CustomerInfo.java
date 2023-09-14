package com.example.daitso.customer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInfo {
	private String customerNm;
	private String customerEmail;
	private String customerTelno;
	private int shippingId;
	private int recentShippingId;
}
