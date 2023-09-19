package com.example.daitso.purchase.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseSuccess {
	private int customerId;
	private int shippingId;
	private String shippingReceiverNm;
	private String shippingReceiverTelno;
	private String shippingRoadNmAddr;
	private String shippingDaddr;
	private String shippingDmnd;
	private String purchaseNum;
	private String totalCost;
	private String orderRequest;	
	private String totalProductPrice;
	private String discountPrice;
}
