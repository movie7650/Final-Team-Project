package com.example.daitso.mypage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.customercoupon.model.SelectCustomerCoupon;
import com.example.daitso.customercoupon.repository.ICustomerCouponRepository;
import com.example.daitso.customercoupon.service.CustomerCouponService;
import com.example.daitso.customercoupon.service.ICustomerCouponService;
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
	@Autowired
	PasswordEncoder pwEncoder;
	@Autowired
	ICustomerCouponService customerCouponService;
	
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
		//상단에 잔여 포인트 출력 
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint",point + "P");
		
		//구매 목록 출력
		List<Purchase> purchaseList = purchaseService.selectAllPurchase();
		model.addAttribute("purchaseList",purchaseList);
		
		//구매상품이름출력
		List<PurchaseCheck> purchasecheckList=purchaseService.selectAllProductNM();
		model.addAttribute("purchaseCheckList",purchasecheckList);
	
		
		return "mypage/order-list";
	}
	
	//마이페이지-주문조회-결제취소
	@RequestMapping("/canclepay")
	public String canclePay(Model model) {
		//상단 잔여포인트 출력
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		
		//구매 목록 출력
		List<Purchase> purchaseList = purchaseService.selectAllPurchase();
		model.addAttribute("purchaseList",purchaseList);
		
		//구매상품이름출력
		List<PurchaseCheck> purchasecheckList=purchaseService.selectAllProductNM();
		model.addAttribute("purchaseCheckList",purchasecheckList);
		
		return "mypage/cancle-pay";
	}
	//마이페이지-주문조회-배송중
	@RequestMapping("/searchshipping")
	public String searchShipping(Model model) {
		//상단 잔여포인트 출력
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		
		//구매 목록 출력
		List<Purchase> purchaseList = purchaseService.selectAllPurchase();
		model.addAttribute("purchaseList",purchaseList);
		
		//구매상품이름출력
		List<PurchaseCheck> purchasecheckList=purchaseService.selectAllProductNM();
		model.addAttribute("purchaseCheckList",purchasecheckList);

		return "mypage/mypage-search-shipping";
	}
	//마이페이지-주문주회-배송완료
	@RequestMapping("/shippingcomplete")
	public String shippingComplete(Model model) {
		//상단 잔여포인트
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		
		//구매 목록 출력
		List<Purchase> purchaseList = purchaseService.selectAllPurchase();
		model.addAttribute("purchaseList",purchaseList);
		
		//구매상품이름출력
		List<PurchaseCheck> purchasecheckList=purchaseService.selectAllProductNM();
		model.addAttribute("purchaseCheckList",purchasecheckList);

		return "mypage/mypage-shipping-complete";
	}
	
	
	//마이페이지-쿠폰등록 및 사용가능쿠폰조회 컨트롤러
	@RequestMapping("/mycoupon")
	public String insertCoupon(Model model) {
		//상단 잔여포인트
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		//사용가능한 쿠폰리스트 출력
		List<SelectCustomerCoupon> selectUsableCustomerCouponList = customerCouponService.selectUsableCoupon();
		model.addAttribute("selectCustomerCouponList",selectUsableCustomerCouponList);
		return "mypage/insert-coupon";
	}
	
	//마이페이지-쿠폰등록 및 쿠폰사용완료리스트 컨트롤러
	@RequestMapping("/mycoupon-used")
	public String usedCoupon(Model model) {
		
		//상단 잔여포인트
		int point = pointService.selectTotalPoint();
		model.addAttribute("totalPoint", point + "P");
		
		//사용완료 쿠폰리스트 출력
		List<SelectCustomerCoupon> selectBanCustomerCouponList = customerCouponService.selectBanCoupon();
		model.addAttribute("banCustomerCouponList",selectBanCustomerCouponList);
		
		return "mypage/mycoupon-used";
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
