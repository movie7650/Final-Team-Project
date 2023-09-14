package com.example.daitso.shipping.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShippingAdd {
	private String shippingReceiverNm;
	private String shippingReceiverTelno;
	private String shippingRoadNmAddr;
	private String shippingDaddr;
	private String shippingDmnd;
	private String shippingDv;
}
