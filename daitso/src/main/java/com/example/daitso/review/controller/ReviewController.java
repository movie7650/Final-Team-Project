package com.example.daitso.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.review.service.IReviewService;

@Controller
@RequestMapping("/mypage")
public class ReviewController {
	
	@Autowired
	IReviewService reviewService;
	 //리뷰 페이지 불러오기
	@RequestMapping("/review")
	public String Review() {
		return "mypage/review";
		
	}
	@RequestMapping("/orderlist")
	public String OrderList() {
		return "mypage/order-list";
	}
	
}
