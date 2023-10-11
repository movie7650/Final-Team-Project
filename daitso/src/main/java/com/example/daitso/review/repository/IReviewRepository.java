package com.example.daitso.review.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.review.model.MypageReviewCheck;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;
import com.example.daitso.review.model.WriteMyReview;

@Repository
@Mapper
public interface IReviewRepository {
	
	//productId와 customerId와 purchaseId를 보고 작성한 리뷰 갯수 카운트
	int countCusProPurId(@Param("customerId")int customerId, @Param("productId") int productId, @Param("purchaseNum") String purchaseNum );
	
	// 리뷰 작성하기
	void insertReview(WriteMyReview writeMyReview);
	//내가 주문한 상품의 상품정보가져오기
	List<WriteMyReview> selectMyPurchase(@Param("customerId") int customerId, @Param("productId") int productId, @Param("purchaseNum") String purchaseNum, @Param("purchaseId") int purchaseId);
	
	List<MypageReviewCheck> selectReviewAll(int customerId);
	
	void selectReview(int reviewId);
	
	void updateReview(Review review);
	
	void deleteReview(@Param("reviewId") int reviewId);
	
	// 특정 상품에 대한 리뷰 조회하기
	List<ReviewProductDetail> selectProductReview(@Param("groupId") int groupId, @Param("start") int start, @Param("end") int end, @Param("customerId") int customerId);
	
	// 특정 상품에 대한 리뷰 개수 갖고오기
	int selectProductReviewCount(int groupId);

	//리뷰내용갯수 세기
	int selectReviewContentCount(int customerId);
	
	// 특정 상품에 대한 리뷰 평균 구하기
	int selectProductReviewAvg(int groupId);
	
	// 리뷰 좋아요 삽입하기
	void insertReviewHeart(@Param("reviewId") int reviewId, @Param("customerId") int customerId);
	
	// 리뷰 좋아요 개수 갖고오기
	int selectReviewHeartCount(int reviewId);
}
