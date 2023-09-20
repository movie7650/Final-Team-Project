package com.example.daitso.inquiry.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryInfo {
	private int productId;
	private String productNm;
	private String inquiryContent;
	private String createDt;
}
