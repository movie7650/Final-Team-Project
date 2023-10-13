package com.example.daitso.purchase.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PurchaseDetailCheck {
	private int customerId;  
	private String productNm;
	private int productPrice;
	private String purchaseNum;
	private int purchaseCount;
	private String purchasePrice;
	private String totalCost;
	private int purchaseDv;
	private Date createDt;
	private String shippingReceiverNm;
	private String shippingReceiverTelno;
	private String shippingRoadNmAddr;
	private String shippingDaddr;
	private String shippingDmnd;
	private String productImg;
}
