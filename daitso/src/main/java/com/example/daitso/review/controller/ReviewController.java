package com.example.daitso.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.review.service.IReviewService;

@Controller
public class ReviewController {
	
	@Autowired
	IReviewService reviewService;
	
	@RequestMapping("/review")
	public String header() {
		return "mypage/review";
		
	}
	
}
