package com.example.daitso.purchase.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseCheck {
	private int customerId;
	private String productNm;
	private int productPrice;
	private String purchaseNum;
	private int purchaseCount;
	private String purchasePrice;
	private String totalCost;
	private String purchaseDv;
}
