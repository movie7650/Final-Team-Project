package com.example.daitso.cart.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCoupon {
	private int productId;
	private String productNm;
	private String productPrice;
	private int cartId;
	private int cartCount;
	private String cartPrice;
	private int categoryId;
	private String productImageFirst;
	private String productOptionFirst;
	private String productOptionSecond;
	private String productOptionThird;
	private int couponUseCount;
}
