package com.example.daitso.inquiry.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InquiryProduct {
	private int customerId;
	private int productId;
	private String productOptionFirst;
	private String productOptionSecond;
	private String productOptionThird;
	private String inquiryContent;
	private Date createDt;
	private int level;
}
