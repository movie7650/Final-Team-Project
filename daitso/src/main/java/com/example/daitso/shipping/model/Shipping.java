package com.example.daitso.shipping.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Shipping {
	private int shippingId;
	private int customerId;
	private String shippingRecieverNm;
	private String shippingReceiverTelno;
	private String shippingDmnd;
	private String shippingRoadNmAddr;
	private String shippingDaddr;
	private int shippingDv;
	private String status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
}
