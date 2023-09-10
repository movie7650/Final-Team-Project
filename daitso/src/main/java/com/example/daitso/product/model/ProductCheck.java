package com.example.daitso.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductCheck {
	private int productGroupId;
	private int productId;
	private int categoryId;
	private String productNm;
	private String productContent;
	private int productPrice;
	private int productStock;
	private String productOptionFirst;
	private String productOptionSecond;
	private String productOptionThird;
	private String productImageFirst;
	private String productImageSecond;
	private String productImageThird;
	private String productCode;
	private int subCategoryId;
	private int categoryPrId;
	private int rn;
	private String categoryNm;
}
