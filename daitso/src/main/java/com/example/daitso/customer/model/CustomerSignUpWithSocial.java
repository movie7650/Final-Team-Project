package com.example.daitso.customer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerSignUpWithSocial {
	private String customerEmail;
	private String customerNm;
	private String loginMethod;
	private String customerPw;
}
