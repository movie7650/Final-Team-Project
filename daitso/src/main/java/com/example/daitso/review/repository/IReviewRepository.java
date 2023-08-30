package com.example.daitso.review.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.review.model.Review;

@Repository
@Mapper
public interface IReviewRepository {
	
	void insertReview(Review review);
	
	List<Review> selectReviewAll();
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(Review review);
	
	List<Review> selectProductReview(int productId);

	int selectProductReviewAvg(int productId);
}
