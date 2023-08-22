package com.example.daitso.inquiry.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Inquiry {
	private int inquiryId;
	private int productId;
	private int customerId;
	private int inquiryPrId;
	private String inquiryContent;
	private String inquiryAnsDv;
	private char status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
}
