package com.example.daitso.customer.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Customer {
	private int customerId;
	private String customerEmail;
	private String customerPw;
	private String customerNm;
	private String customerTelno;
	private int customerDv;
	private char status;
	private Date creatDt;
	private String creator;
	private Date modifyDt;
	private String modifier;

}
