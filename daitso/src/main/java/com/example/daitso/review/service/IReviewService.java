package com.example.daitso.review.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.daitso.review.model.MypageReviewCheck;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;
import com.example.daitso.review.model.WriteMyReview;

public interface IReviewService {
	
	// 리뷰 작성하기
	void insertReview(WriteMyReview writeMyReview);
	
	//내가 주문한 상품의 상품정보 가져오기
	List<WriteMyReview> selectMyPurchase(@Param("customerId") int customerId, @Param("productId") int productId, @Param("purchaseNum") String purchaseNum);

	List<MypageReviewCheck> selectReviewAll(int customerId);
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(@Param("customerId") int customerId, @Param("reviewId") int reviewId);
	
	//리뷰내용갯수 세기
	int selectReviewContentCount(int customerId);

	// 특정 상품에 대한 리뷰 조회하기
	List<ReviewProductDetail> selectProductReview(int groupId, int page, int customerId);
	
	// 특정 상품에 대한 리뷰 개수 갖고오기
	int selectProductReviewCount(int groupId);
	
	// 특정 상품에 대한 리뷰 평균 구하기
	int selectProductReviewAvg(int groupId);
	
	// 리뷰 좋아요 삽입한 후 좋아요 개수 갖고오기
	int insertReviewHeart(int reviewId, int customerId);
}
