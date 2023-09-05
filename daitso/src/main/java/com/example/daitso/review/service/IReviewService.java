package com.example.daitso.review.service;

import java.util.List;

import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;

public interface IReviewService {
	
	void insertReview(Review review);
	
	List<Review> selectReviewAll();
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(Review review);

	List<ReviewProductDetail> selectProductReview(int groupId, int page);
	
	int selectProductReviewCount(int groupId);
	
	int selectProductReviewAvg(int groupId);
}
