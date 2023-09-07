package com.example.daitso.cart.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCheck {
	private int cartId;
	private int productId;
	private int cartCount;
	private String purchaseNum;
	private String productNm;
	private String productPrice;
	private String cartPrice;
	private int productStock;
	private String productOptionFirst;
	private String productOptionSecond;
	private String productOptionThird;
	private String productImageFirst;	
	private int categoryId;
	private String checked;
	private char productStatus; 
	private int categoryPrId;
}
