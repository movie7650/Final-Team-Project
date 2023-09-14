package com.example.daitso.shipping.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingInfo {
	private int shippingId;
	private String shippingReceiverNm;
	private String shippingReceiverTelno;
	private String shippingRoadNmAddr;
	private String shippingDaddr;
	private String shippingDmnd;
	private String shippingDv;
}
