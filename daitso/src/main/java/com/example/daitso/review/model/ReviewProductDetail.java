package com.example.daitso.review.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewProductDetail {
	private int reviewId;
	private int productId;
	private String reviewTitle;
	private String reviewContent;
	private int reviewStarPoint;
	private int reviewHeartCount;
	private String reviewImageFirst;
	private String reviewImageSecond;
	private String reviewImageThird;
	private String status;
	private Date createDt;
	private String customerNm;
	private String productNm;
	private String options;
}
