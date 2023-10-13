package com.example.daitso.review.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MypageReviewCheck {
	private int reviewId;
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
	private int reviewContentCount;
	private String productImg;
}
