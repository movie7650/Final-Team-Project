package com.example.daitso.review.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Review {
	private int reviewId;
	private int productId;
	//private int customerId; 필요시 사용
	private String reviewContent;
	private int reviewStarPoint;
	private int reviewHeartCount;
	private String reviewImageFirst;
	private String reviewImageSecond;
	private String reviewImageThird;
	private String status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
	private String customerNm;
}
