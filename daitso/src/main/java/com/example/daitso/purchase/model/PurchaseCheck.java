package com.example.daitso.purchase.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseCheck {
	private int productId;
	private int purchaseId;
	private int customerId;
	private String productNm;
	private int productPrice;
	private String purchaseNum;
	private int purchaseCount;
	private String purchasePrice;
	private String totalCost;
	private String purchaseDv;
	private String productImg;
	private Date createDt;
	private int shippingCompleteCount;
	private int shippingCount;
	private int payCoinCount;
	private int purchaseNumCount;
	private int reviewCount;
	
}
