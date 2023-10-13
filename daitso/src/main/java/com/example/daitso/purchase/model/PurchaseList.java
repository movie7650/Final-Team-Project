package com.example.daitso.purchase.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseList {
	private int purchaseId;
	private int productId;
	private int customerId;
	private int shippingId;
	private String purchaseNum;
	private int purchaseCount;
	private int purchasePrice;
	private int totalCost;
	private String orderRequest;
	private int purchaseDv;
	private String status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
	
	private String customerNm;
	private String commonCodeNm;
	private String productNm;
	private int commonCodeId;
	private int rn;
	private String searchText;
	
}