package com.example.daitso.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.review.model.MypageReviewCheck;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;
import com.example.daitso.review.repository.IReviewRepository;

@Service
public class ReviewService implements IReviewService{
	
	@Autowired
	IReviewRepository reviewRepository;

	@Override
	public void insertReview(Review review) {
		reviewRepository.insertReview(review);		
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
	public void deleteReview(int customerId, int reviewId) {
		 reviewRepository.deleteReview(customerId, reviewId);
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

}
