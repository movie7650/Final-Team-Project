package com.example.daitso.inquiry.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class InquiryInsertDTO {
	private int productGroupId;
	private String size;
	private String color;
	private String other;
	private String content;
	private int customerId;
}
