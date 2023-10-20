package com.example.daitso.review.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.review.model.ReviewHeartDTO;
import com.example.daitso.review.repository.IReviewRepository;

@SpringBootTest
class ReviewTest {
	
	@Autowired
	IReviewService reviewService;
	
	@Autowired
	IReviewRepository reviewRepository;

	@Test
	@Transactional
	void 리뷰좋아요() {
		//given
		int resultBefore = reviewRepository.selectReviewHeartCount(83);
		ReviewHeartDTO testDTO = ReviewHeartDTO.builder()
									.customerId(17)
									.reviewId(83)
									.build();
		//when
		int result = reviewService.insertReviewHeart(testDTO);
		
		//then
		Assertions.assertThat(result).isEqualTo(resultBefore + 1);
	}

}
