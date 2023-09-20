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
	
	// 리뷰 삽입하기
	void insertReview(Review review);
	
	List<MypageReviewCheck> selectReviewAll(int customerId);
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(@Param("customerId") int customerId, @Param("reviewId") int reviewId);
	
	// 특정 상품에 대한 리뷰 조회하기
	List<ReviewProductDetail> selectProductReview(@Param("groupId") int groupId, @Param("start") int start, @Param("end") int end, @Param("customerId") int customerId);
	
	// 특정 상품에 대한 리뷰 개수 갖고오기
	int selectProductReviewCount(int groupId);

	// 특정 상품에 대한 리뷰 평균 구하기
	int selectProductReviewAvg(int groupId);
	
	// 리뷰 좋아요 삽입하기
	void insertReviewHeart(@Param("reviewId") int reviewId, @Param("customerId") int customerId);
	
	// 리뷰 좋아요 개수 갖고오기
	int selectReviewHeartCount(int reviewId);
}
