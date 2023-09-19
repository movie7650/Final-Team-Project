package com.example.daitso.purchase.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseInsert {
	private int cartId;
	private int customerCouponId;
	private int customerId;
	private int shippingId;
	private String purchaseNum;
	private int totalCost;
	private String orderRequest;
}
