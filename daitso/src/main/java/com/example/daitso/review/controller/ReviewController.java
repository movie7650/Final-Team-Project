package com.example.daitso.review.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.review.model.ReviewHeartDTO;
import com.example.daitso.review.model.ReviewProductDetail;
import com.example.daitso.review.service.IReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	IReviewService reviewService;

	//상품 리뷰 페이징
	@GetMapping("/{productGroupId}/{page}/{customerId}")
	@ResponseBody
	public List<ReviewProductDetail> reviewPaging(@PathVariable int productGroupId, @PathVariable int page, @PathVariable int customerId){
		return reviewService.selectProductReview(productGroupId, page, customerId);
	}
	
	//상품 리뷰 좋아요
	@PostMapping("/heart/update")
	@ResponseBody
	public Integer reviewHeart(@RequestBody ReviewHeartDTO reviewHeartDTO){
		return reviewService.insertReviewHeart(reviewHeartDTO.getReviewId(), reviewHeartDTO.getCustomerId());
	}
}
