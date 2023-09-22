package com.example.daitso.inquiry.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InquiryInsertDTO {
	private int productGroupId;
	private String size;
	private String color;
	private String other;
	private String content;
}
