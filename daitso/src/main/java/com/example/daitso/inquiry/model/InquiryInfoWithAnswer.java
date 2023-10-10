package com.example.daitso.inquiry.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryInfoWithAnswer {
	private int productId;
	private String productNm;
	private String inquiryContent;
	private String createDt;
	private String inquiryAnswer;
	private String modifyDt;
	private int ansInquiryId;
}
