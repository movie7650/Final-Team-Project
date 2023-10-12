package com.example.daitso.review.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyReview {
	private String productNm;
	private String reviewContent;
	private int reviewStarPoint;
	private String reviewImageFirst;
	private String reviewImageSecond;
	private String reviewImageThird;
	private Date createDt;
	private String reviewTitle;
	private String productImg;
}
