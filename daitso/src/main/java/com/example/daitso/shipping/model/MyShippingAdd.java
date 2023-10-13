package com.example.daitso.shipping.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyShippingAdd {
	private int shippingId;
	private int customerId;
	private String shippingReceiverNM;
	private String shippingReceiverTelNO;
	private String shippingDmnd;
	private String shippingRoadNMAddr;
	private String shippingDaddr;
	private int shippingDv;
}
