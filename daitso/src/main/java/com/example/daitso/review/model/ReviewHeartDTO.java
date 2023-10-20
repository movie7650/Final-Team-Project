package com.example.daitso.review.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ReviewHeartDTO {
	private int customerId;
	private int reviewId;
}
