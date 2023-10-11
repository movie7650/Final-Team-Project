package com.example.daitso.review.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteMyReview {
	//상품정보
	private int purchaseId;
	private String purchaseNum;
	private int purchaseCount;
	private int productId;
	private String productNm;
	private Date purchaseDt;
	private String totalCost;
	private String purchasePrice;
	private String productImg;
	
	//리뷰정보
	private int reviewId;
	private int customerId;
	private String reviewTitle;
	private String reviewContent;
	private int reviewStarPoint;
	private int reviewHeartCount;
	private String reviewImageFirst;
	private String reviewImageSecond;
	private String reviewImageThird;
	private Date createDt;
	private int reviewContentCount;
}
