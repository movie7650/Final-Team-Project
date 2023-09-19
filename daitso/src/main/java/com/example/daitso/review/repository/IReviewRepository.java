package com.example.daitso.review.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.review.model.MypageReviewCheck;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;

@Repository
@Mapper
public interface IReviewRepository {
	
	void insertReview(Review review);
	
	List<MypageReviewCheck> selectReviewAll(int customerId);
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(@Param("customerId") int customerId, @Param("reviewId") int reviewId);
	
	List<ReviewProductDetail> selectProductReview(@Param("groupId") int groupId, @Param("start") int start, @Param("end") int end);
	
	int selectProductReviewCount(int groupId);

	int selectProductReviewAvg(int groupId);
}
