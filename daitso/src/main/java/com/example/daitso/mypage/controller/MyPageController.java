package com.example.daitso.mypage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.point.model.Point;
import com.example.daitso.point.service.IPointService;
import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.service.IPurchaseService;
import com.example.daitso.review.service.IReviewService;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	IPointService pointService;
	@Autowired
	IPurchaseService purchaseService;
	@Autowired
	IReviewService reviewService;
	
	//마이페이지-포인트 컨트롤러 
	@RequestMapping(value="/mypoint", method=RequestMethod.GET)
	public String selectPoint(Model model) {
		List<Point> points = pointService.selectPoint();
		model.addAttribute("points",points);
		int totalPoint = 0;
		for(Point point : points) {
			totalPoint += point.getPointAfter();
		}
		if(totalPoint > 999) {
			model.addAttribute("totalPoint", totalPoint/1000 + "," + totalPoint%1000 + "P");
		}else {
			model.addAttribute("totalPoint", totalPoint + "P");
		}
		return "mypage/my-point";
	}
	
	//마이페이지-주문목록 컨트롤러
	@RequestMapping(value="/orderlist", method=RequestMethod.GET)
	public String selectPurchase(Purchase purchase, Model model, PurchaseCheck purchasecheck) {
		List<PurchaseCheck> purchases = purchaseService.selectAllPurchase();
		model.addAttribute("purchases", purchases.get(0));
		
		//소유포인트 상단바에 띄우기
		List<Point> points = pointService.selectPoint();
		int totalPoint = 0;
		for(Point point : points) {
			totalPoint += point.getPointAfter();
		}
		if(totalPoint > 999) {
			model.addAttribute("totalPoint", totalPoint/1000 + "," + totalPoint%1000 + "P");
		}else {
		model.addAttribute("totalPoint", totalPoint + "P");
		}
		return "mypage/order-list";
	}
	
	//마이페이지-리뷰관리 컨트롤러
	 //리뷰 페이지 불러오기
	@RequestMapping("/review")
	public String Review(Model model) {
		List<Point> points = pointService.selectPoint();
		int totalPoint = 0;
		for(Point point : points) {
			totalPoint += point.getPointAfter();
		}
		if(totalPoint > 999) {
			model.addAttribute("totalPoint", totalPoint/1000 + "," + totalPoint%1000 + "P");
		}else {
		model.addAttribute("totalPoint", totalPoint + "P");
		}
		return "mypage/review";
		
	}
	
	//마이페이지-배송지관리 컨트롤러
	@RequestMapping("/myshipping")
	public String shippingTest(Model model) {
		List<Point> points = pointService.selectPoint();
		int totalPoint = 0;
		for(Point point : points) {
			totalPoint += point.getPointAfter();
		}
		if(totalPoint > 999) {
			model.addAttribute("totalPoint", totalPoint/1000 + "," + totalPoint%1000 + "P");
		}else {
		model.addAttribute("totalPoint", totalPoint + "P");
		}
		return "mypage/my-shipping";
	}
	
	//마이페이지-배송지관리-배송지추가 컨트롤러
	@RequestMapping("/addshipping")
	public String addShipping() {
		
		return "mypage/add-shipping";
	}
	
	//마이페이지-회원정보확인 컨트롤러
	@RequestMapping("/checkuser")
	public String updateMe() {
		
		
		return "mypage/check-user-inform";
	}
	
	//마이페이지-회원정보수정 컨트롤러
	@RequestMapping("/updateuser")
	public String updateUser() {
		return "mypage/update-user-inform";
	}

}
