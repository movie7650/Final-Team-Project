package com.example.daitso.cart.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartPurchase {
	
	private int productId;
	private String productNm;
	private String productOptionFirst;
	private String productOptionSecond;
	private String productOptionThird;
	private int cartId;
	private int cartCount;
	private int cartPrice;
}
