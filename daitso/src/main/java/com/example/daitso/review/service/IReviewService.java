package com.example.daitso.review.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.daitso.review.model.MypageReviewCheck;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;

public interface IReviewService {
	
	void insertReview(Review review);
	
	List<MypageReviewCheck> selectReviewAll(int customerId);
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(@Param("customerId") int customerId, @Param("reviewId") int reviewId);

	List<ReviewProductDetail> selectProductReview(int groupId, int page);
	
	int selectProductReviewCount(int groupId);
	
	int selectProductReviewAvg(int groupId);
}
