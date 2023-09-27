package com.example.daitso.customer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerSocial {
	private String customerNm;
	private String customerEmail;
	private String customerTelno;
}
