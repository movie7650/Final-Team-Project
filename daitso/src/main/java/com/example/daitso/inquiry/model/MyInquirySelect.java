package com.example.daitso.inquiry.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyInquirySelect {
	private int productId;
	private int customerId;
	private String inquiryContent;
	private String productNm;
	private String productImageFirst;
	private int inquiryPrId;
	private String inquiryAnsDv;
	private Date createDt;
	private int creator;
	private int inquiryId;
	private int inquiryStatusYCount;
	private String inquiryReply;
	private String productImg;
}
