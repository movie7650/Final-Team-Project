package com.example.daitso.purchase.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseCheck {
	private int customerId;
	private String productNm;
	private String productPrice;
	private String purchaseNum;
	private int purchaseCount;
	private int purchasePrice;
	private String totalCost;
	private String purchaseDv;
}
