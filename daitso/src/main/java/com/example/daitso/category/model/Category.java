package com.example.daitso.category.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Category {
	private int categoryId;
	private String categoryNm;
	private String categoryContent;
	private int categoryPrId;
	private char status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
	
}
