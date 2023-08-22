package com.example.daitso.cart.model;

import java.sql.Date;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cart {
	private int cartId;
	private int productId;
	private int customerId;
	private int cartCount;
	private String purchaseNum;
	private char status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
}
