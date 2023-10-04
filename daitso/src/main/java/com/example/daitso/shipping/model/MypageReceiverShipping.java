package com.example.daitso.shipping.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MypageReceiverShipping {
	private int customerId;
	private int shippingId;
	private String shippingReceiverNM;
	private String shippingReceiverTelNO;
	private String shippingDmnd;
	private String shippingRoadNMAddr; //도로명주소
	private String shippingDaddr;//상세주소
	private String shippingDv;
	private String status;
}
