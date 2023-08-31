package com.example.daitso.review.service;

import java.util.List;

import com.example.daitso.review.model.Review;

public interface IReviewService {
	
	void insertReview(Review review);
	
	List<Review> selectReviewAll();
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(Review review);

	List<Review> selectProductReview(int productId);
	
	int selectProductReviewAvg(int productId);
}
