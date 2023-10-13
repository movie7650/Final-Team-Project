package com.example.daitso.customer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerSignUp {
	private String customerEmail;
	private String customerPw;
	private String customerRePw;
	private String customerNm;
	private String customerTelno;
}
