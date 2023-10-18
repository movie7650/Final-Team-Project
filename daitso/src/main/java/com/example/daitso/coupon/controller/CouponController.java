package com.example.daitso.coupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.check.ILogincheckService;
import com.example.daitso.coupon.model.CouponEvent;
import com.example.daitso.coupon.model.CouponEventInsert;
import com.example.daitso.coupon.service.ICouponService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/coupon")
public class CouponController {
	
	@Autowired
	ICouponService couponService;
	
	@Autowired
	ILogincheckService logincheckService;
	
	@GetMapping("/download")
	public String download(Model model) {
		// spring security -> 사용자 고유번호 받아오기
		int customerId = logincheckService.loginCheck();
		
		model.addAttribute("customerId", customerId);
		return "/coupon/coupon-main";
	}
	
	@PostMapping("/download")
	@ResponseBody
	public String download(@RequestBody CouponEventInsert coupon) {
		
		int num = couponService.insertEventCoupon(coupon);
		return String.valueOf(num);
	}
	
	@GetMapping("/download/{page}")
	public String download(@PathVariable int page, Model model) {
		
		 List<CouponEvent> couponList = couponService.selectEventCoupon(page);
		 
		 int couponCnt = couponService.countEventCoupon();
		 
		 int totalPage = 0;
		 if(couponCnt > 0) {
			 totalPage = (int)Math.ceil(couponCnt/9.0);
		 }
		 int totalPageBlock = (int)(Math.ceil(totalPage/10.0));
		 int nowPageBlock = (int)(Math.ceil(page/10.0));
		 int startPage = (nowPageBlock-1)*10 + 1;
		 int endPage = 0;
		 if(totalPage > nowPageBlock * 10) {
			 endPage = nowPageBlock * 10;
		 }else {
			 endPage = totalPage;
		 }
		 
		 int customerId = logincheckService.loginCheck();
		
		 model.addAttribute("customerId", customerId);
		  
		 model.addAttribute("couponList", couponList);
		 model.addAttribute("totalPageCount", totalPage);
		 model.addAttribute("totalPageBlock", totalPageBlock);
		 model.addAttribute("nowPage", page);
		 model.addAttribute("nowPageBlock", nowPageBlock);
		 model.addAttribute("startPage", startPage);
		 model.addAttribute("endPage", endPage);
		 
		return "/coupon/coupon-sub";
	}

}
