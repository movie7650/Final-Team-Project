package com.example.daitso.review.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MypageReviewCheck {
	private int customerId;
	private int productId;
	private String productNm;
	private String reviewTitle;
	private String reviewContent;
	private int reviewStarPoint;
	private int reviewHeartCount;
	private String reviewImageFirst;
	private String reviewImageSecond;
	private String reviewImageThird;
	private Date createDt;
}
