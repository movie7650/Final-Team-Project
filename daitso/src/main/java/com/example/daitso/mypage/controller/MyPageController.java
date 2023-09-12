package com.example.daitso.mypage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.point.model.Point;
import com.example.daitso.point.model.TotalPoint;
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
	@Autowired
	PasswordEncoder pwEncoder;
	
	//마이페이지-포인트 컨트롤러 
	@RequestMapping(value="/mypoint", method=RequestMethod.GET)
	public String selectPoint(Model model) {
		List<Point> points = pointService.selectPoint();
		int point = pointService.selectTotalPoint();
		model.addAttribute("points",points);
		model.addAttribute("totalPoint", point + "P");
		
		return "mypage/my-point";
	}
	
	//마이페이지-주문목록 컨트롤러
	@RequestMapping(value="/orderlist", method=RequestMethod.GET)
	public String selectPurchase(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint",point + "P");
		return "mypage/order-list";
	}
	
	//마이페이지-리뷰관리 컨트롤러
	 //리뷰 페이지 불러오기
	@RequestMapping("/review")
	public String Review(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint",point + "P");
		
		return "mypage/review";
		
	}
	
	//마이페이지-배송지관리 컨트롤러
	@RequestMapping("/myshipping")
	public String shippingTest(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint",point + "P");
		return "mypage/my-shipping";
	}
	
	//마이페이지-배송지관리-배송지추가 컨트롤러
	@RequestMapping("/addshipping")
	public String addShipping(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		
		return "mypage/add-shipping";
	}
	
	//마이페이지-회원정보확인 컨트롤러
	@RequestMapping("/checkuser")
	public String checkIform(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/check-user-inform";
	}
	
	//마이페이지-회원정보수정 컨트롤러
	@RequestMapping("/updateuser")
	public String updateUser(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/update-user-inform";
	}
	
	//마이페이지-쿠폰등록 컨트롤러
	@RequestMapping("/mycoupon")
	public String insertCoupon(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/insert-coupon";
	}
	//마이페이지-쿠폰등록-쿠폰사용완료페이지
	@RequestMapping("/mycoupon-used")
	public String usedCoupon(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/mycoupon-used";
	}
	
	//마이페이지-주문조회-결제취소
	@RequestMapping("/canclepay")
	public String canclePay(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/cancle-pay";
	}
	
	//마이페이지-주문주회-배송중
	@RequestMapping("/searchshipping")
	public String searchShipping(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/mypage-search-shipping";
	}
	
	//마이페이지-주문주회-배송완료
	@RequestMapping("/shippingcomplete")
	public String shippingComplete(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/mypage-shipping-complete";
	}
	
	//마이페이지-내문의관리 
	@RequestMapping("/myinquiry")
	public String myInquiry(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/mypage-inquiry";
	}
	
	//마이페이지-상세주문
	@RequestMapping("/mydetailorder")
	public String detaiOrder(Model model) {
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		return "mypage/detail-order";
	}
	

}
