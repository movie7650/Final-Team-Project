package com.example.daitso.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.review.model.Review;
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
	public List<Review> selectProductReview(int productId) {
		return reviewRepository.selectProductReview(productId);
	}

	@Override
	public int selectProductReviewAvg(int productId) {
		return reviewRepository.selectProductReviewAvg(productId);
	}

}
