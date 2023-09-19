package com.example.daitso.mypage.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.customercoupon.model.SelectCustomerCoupon;
import com.example.daitso.customercoupon.service.ICustomerCouponService;
import com.example.daitso.point.model.Point;
import com.example.daitso.point.service.IPointService;
import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.model.PurchaseDetailCheck;
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
	@Autowired
	ICustomerCouponService customerCouponService;
	
	//마이페이지-포인트 컨트롤러 
	@RequestMapping(value="/mypoint", method=RequestMethod.GET)
	public String selectPoint(Model model) {
		
		try {
			//로그인확인
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			
			//상단에 배송완료 갯수 출력
			int shipCompleteCount = purchaseService.selectShippingComplete(customerId);
			model.addAttribute("shippingCompleteCount",shipCompleteCount);
			//상단에 배송중갯수 출력
			int shipCount01 = purchaseService.selectShipping(customerId);
			model.addAttribute("shipCount",shipCount01);
			
			List<Point> points = pointService.selectPoint(customerId);
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("points",points);
			model.addAttribute("totalPoint", point + "P");  
			
			return "mypage/my-point";
			
		}catch(ClassCastException e) {
			return "redirect:/login";
			
		}
	}
	
	//마이페이지-주문목록 컨트롤러
	@RequestMapping(value="/orderlist", method=RequestMethod.GET)
	public String selectPurchase(Model model) {
		try {
			//로그인확인
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단에 배송완료 갯수 출력
			int shipCompleteCount = purchaseService.selectShippingComplete(customerId);
			model.addAttribute("shippingCompleteCount",shipCompleteCount);

			//상단에 배송중갯수 출력
			int shipCount01 = purchaseService.selectShipping(customerId);
			model.addAttribute("shipCount",shipCount01);
			//상단에 잔여 포인트 출력 
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint",point + "P");
			
			//구매 목록 출력
			List<PurchaseCheck> purchaseList = purchaseService.selectAllOrderProduct(customerId);
			model.addAttribute("purchaseList",purchaseList);
			
		return "mypage/order-list";
		}catch(ClassCastException e){
			
			return "redirect:/customer/login";
			
		}
		
	}
	
	//마이페이지-상세주문
	@RequestMapping(value="/mydetailorder", method=RequestMethod.GET)
	public String detailOrder(String purchaseNum,Model model) {
		try {
			//로그인 
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			
			//상단에 배송완료 갯수 출력
			int shipCompleteCount = purchaseService.selectShippingComplete(customerId);
			model.addAttribute("shippingCompleteCount",shipCompleteCount);

			//상단에 배송중갯수 출력
			int shipCount01 = purchaseService.selectShipping(customerId);
			model.addAttribute("shipCount",shipCount01);
			
			//상단에 잔여 포인트 출력 
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint",point + "P");
			
			//구매 목록 출력
//			List<PurchaseCheck> purchaseList = purchaseService.selectAllOrderProduct(customerId);
//			model.addAttribute("purchaseList",purchaseList);
			
			//주문한 상품 상세정보 출력 
			List<PurchaseDetailCheck> purchaseCheckList = purchaseService.selectDetailPurchase(customerId, purchaseNum);
			System.out.println("-------------------------------------");
			System.out.println(purchaseCheckList.get(0).toString());
			System.out.println("-------------------------------------");
			model.addAttribute("purchaseDetailList",purchaseCheckList);
			
		
		return "mypage/detail-order";	
		}catch(ClassCastException e){
			return "redirect:/customer/login";
		}
		
	}
	
	
	//마이페이지-주문조회-입금/결제
	@RequestMapping("/canclepay")
	public String canclePay(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단에 배송완료 갯수 출력
			int shipCompleteCount = purchaseService.selectShippingComplete(customerId);
			model.addAttribute("shippingCompleteCount",shipCompleteCount);

			//상단에 배송중갯수 출력
			int shipCount01 = purchaseService.selectShipping(customerId);
			model.addAttribute("shipCount",shipCount01);
			//상단에 잔여 포인트 출력 
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint",point + "P");
			
			//구매 목록 출력
			List<PurchaseCheck> purchaseList = purchaseService.selectAllOrderProduct(customerId);
			model.addAttribute("purchaseList",purchaseList);
			return "mypage/cancle-pay";
			
		}catch(ClassCastException e){
			return "redirect:/customer/login";
		}
	}
	//마이페이지-주문조회-배송중
	@RequestMapping("/searchshipping")
	public String searchShipping(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단에 배송완료 갯수 출력
			int shipCompleteCount = purchaseService.selectShippingComplete(customerId);
			model.addAttribute("shippingCompleteCount",shipCompleteCount);

			//상단에 배송중갯수 출력
			int shipCount01 = purchaseService.selectShipping(customerId);
			model.addAttribute("shipCount",shipCount01);
			//상단에 잔여 포인트 출력 
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint",point + "P");
			
			//구매 목록 출력
			List<PurchaseCheck> purchaseList = purchaseService.selectAllOrderProduct(customerId);
			model.addAttribute("purchaseList",purchaseList);
			return "mypage/mypage-search-shipping";
			
		}catch(ClassCastException e){
			return "redirect:/customer/login";
		}
	}
	//마이페이지-주문주회-배송완료
	@RequestMapping("/shippingcomplete")
	public String shippingComplete(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단에 배송완료 갯수 출력
			int shipCompleteCount = purchaseService.selectShippingComplete(customerId);
			model.addAttribute("shippingCompleteCount",shipCompleteCount);

			//상단에 배송중갯수 출력
			int shipCount01 = purchaseService.selectShipping(customerId);
			model.addAttribute("shipCount",shipCount01);
			//상단에 잔여 포인트 출력 
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint",point + "P");
			
			//주문 목록 출력
			List<PurchaseCheck> purchaseList = purchaseService.selectAllOrderProduct(customerId);
			model.addAttribute("purchaseList",purchaseList);
			return "mypage/mypage-shipping-complete";
			
		}catch(ClassCastException e){
			return "redirect:/customer/login";
		}
	}
	
	
	//마이페이지-쿠폰등록 및 사용가능쿠폰조회 컨트롤러
	@RequestMapping("/mycoupon")
	public String insertCoupon(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			//사용가능한 쿠폰리스트 출력
			List<SelectCustomerCoupon> selectUsableCustomerCouponList = customerCouponService.selectUsableCoupon(customerId);
			model.addAttribute("selectCustomerCouponList",selectUsableCustomerCouponList);
			return "mypage/insert-coupon";
			
		}catch(ClassCastException e) {
			return "redirect:/customer/login";
		}
	}
	
	//마이페이지-쿠폰등록 및 쿠폰사용완료리스트 컨트롤러
	@RequestMapping("/mycoupon-used")
	public String usedCoupon(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			//사용완료 쿠폰리스트 출력
			List<SelectCustomerCoupon> selectBanCustomerCouponList = customerCouponService.selectBanCoupon(customerId);
			model.addAttribute("banCustomerCouponList",selectBanCustomerCouponList);
			return "mypage/mycoupon-used";
		}catch(ClassCastException e) {
			return "redirect:/customer/login";
		}
	
	}
	
	//마이페이지-리뷰관리 컨트롤러
	 //리뷰 페이지 불러오기
	@RequestMapping("/review")
	public String Review(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			return "mypage/review";
		}catch(ClassCastException e) {
			return "redirect:/customer/login";
		}
	}
	
	//마이페이지-배송지관리 컨트롤러
	@RequestMapping("/myshipping")
	public String shippingTest(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			return "mypage/my-shipping";
			}catch(ClassCastException e) {
				return "redirect:/customer/login";
			}
	}
	
	//마이페이지-배송지관리-배송지추가 컨트롤러
	@RequestMapping("/addshipping")
	public String addShipping(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			return "mypage/add-shipping";
		}catch(ClassCastException e) {
			return "redirect:/customer/login";
		}
		
	}
	
	//마이페이지-회원정보확인 컨트롤러
	@RequestMapping("/checkuser")
	public String checkIform(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			return "mypage/check-user-inform";
		}catch(ClassCastException e) {
			return "redirect:/customer/login";
		}
	}
	
	//마이페이지-회원정보수정 컨트롤러
	@RequestMapping("/updateuser")
	public String updateUser(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			return "mypage/update-user-inform";
		}catch(ClassCastException e) {
			return "redirect:/customer/login";
		}
	}
	
	
	
	
	//마이페이지-내문의관리 
	@RequestMapping("/myinquiry")
	public String myInquiry(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails)principal;
			int customerId = Integer.valueOf(userDetails.getUsername());
			//상단 잔여포인트
			String point = pointService.selectTotalPoint(customerId);
			if(point == null) {
				point = "0";
			}
			model.addAttribute("totalPoint", point + "P");
			return "mypage/mypage-inquiry";
		}catch(ClassCastException e) {
			return "redirect:/customer/login";
		}
		
	}
	
	

}
