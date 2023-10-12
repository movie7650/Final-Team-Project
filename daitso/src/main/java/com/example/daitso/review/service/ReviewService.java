package com.example.daitso.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.review.model.MyReview;
import com.example.daitso.review.model.MypageReviewCheck;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;
import com.example.daitso.review.model.WriteMyReview;
import com.example.daitso.review.repository.IReviewRepository;

@Service
public class ReviewService implements IReviewService{
	
	@Autowired
	IReviewRepository reviewRepository;

	@Override
	public void insertReview(WriteMyReview writeMyReveiw) {
		reviewRepository.insertReview(writeMyReveiw);		
	}
	//내 리뷰 조회하기
	@Override
	public List<MypageReviewCheck> selectReviewAll(int customerId) {
		return reviewRepository.selectReviewAll(customerId);
	}

	@Override
	public void selectReview(int reviewId) {
		reviewRepository.selectReview(reviewId);
	}

	@Override
	public void updateReview(Review review) {
		reviewRepository.updateReview(review);
	}
	//내 리뷰 삭제하기
	@Override
	public void deleteReview(int reviewId) {
		 reviewRepository.deleteReview(reviewId);
	}

	@Override
	public List<ReviewProductDetail> selectProductReview(int groupId, int page, int customerId) {
		int start = (page-1)*2 + 1; 
		return reviewRepository.selectProductReview(groupId, start, start+1, customerId);
	}

	@Override
	public int selectProductReviewAvg(int groupId) {
		return reviewRepository.selectProductReviewAvg(groupId);
	}

	@Override
	public int selectProductReviewCount(int groupId) {
		return reviewRepository.selectProductReviewCount(groupId);
	}

	@Override
	public int insertReviewHeart(int reviewId, int customerId) {
		reviewRepository.insertReviewHeart(reviewId, customerId);
		int num = reviewRepository.selectReviewHeartCount(reviewId);
		return num;
	}
	@Override
	public int selectReviewContentCount(int customerId) {
		return reviewRepository.selectReviewContentCount(customerId);
	}
	//리뷰작성하기-내가주문한 상품 상품정보가져오기
	@Override
	public List<WriteMyReview> selectMyPurchase(int customerId, int productId, String purchaseNum, int purchaseId) {
		return reviewRepository.selectMyPurchase(customerId, productId,purchaseNum, purchaseId);
	}
	
	//customerId와 productId와 purchaseNum에 따른 리뷰 갯수 카운트
	@Override
	public int countCusProPurId(int customerId, int productId, String purchaseNum) {
		return reviewRepository.countCusProPurId(customerId, productId, purchaseNum);
	}
	//reviewId에 따른 리뷰가져오기 
	@Override
	public MyReview selectMyReview(int reviewId) {
		return reviewRepository.selectMyReview(reviewId);
	}

}
