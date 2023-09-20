package com.example.daitso.inquiry.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquirySelect {
	private int inquiryId; 
	private int productId; 
	private String productNm;
	private int categoryId;
	private int customerId;
	private int inquiryPrId;
	private String inquiryContent;
}
