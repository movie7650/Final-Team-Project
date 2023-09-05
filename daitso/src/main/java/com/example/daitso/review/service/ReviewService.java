package com.example.daitso.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public List<Review> selectReviewAll() {
		return reviewRepository.selectReviewAll();
	}

	@Override
	public void selectReview(int reviewId) {
		reviewRepository.selectReview(reviewId);
	}

	@Override
	public void updateReview(Review review) {
		reviewRepository.updateReview(review);
	}

	@Override
	public void deleteReview(Review review) {
		reviewRepository.deleteReview(review);
		
	}

	@Override
	public List<ReviewProductDetail> selectProductReview(int groupId, int page) {
		int start = (page-1)*2 + 1; 
		return reviewRepository.selectProductReview(groupId, start, start+1);
	}

	@Override
	public int selectProductReviewAvg(int groupId) {
		return reviewRepository.selectProductReviewAvg(groupId);
	}

	@Override
	public int selectProductReviewCount(int groupId) {
		return reviewRepository.selectProductReviewCount(groupId);
	}

}
