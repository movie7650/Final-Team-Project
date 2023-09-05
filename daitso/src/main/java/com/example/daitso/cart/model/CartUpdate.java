package com.example.daitso.cart.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CartUpdate {
	private int cartCount;
	private String purchaseNum;
	private int cartId;
}
