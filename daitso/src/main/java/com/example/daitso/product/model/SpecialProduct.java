package com.example.daitso.product.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SpecialProduct {
	private int specialProductId;
	private int specialProductGroupId;
	private String specialProductNm;
	private String specialProductDv;
	private int categoryId;
	private int productPrice;
	private float reviewStarPoint;
	private int rCnt;
	private Date createDt;
	private String creator;
}
