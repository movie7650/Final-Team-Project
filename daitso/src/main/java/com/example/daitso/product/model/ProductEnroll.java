package com.example.daitso.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEnroll {
	private int productId;
	private int categoryId;
	private String productNm;
	private String productContent;
	private int productPrice;
	private int productStock;
	private String productOptionFirst;
	private String productOptionSecond;
	private String productOptionThird;
}
