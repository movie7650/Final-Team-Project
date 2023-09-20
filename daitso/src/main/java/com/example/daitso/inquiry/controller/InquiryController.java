package com.example.daitso.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.inquiry.service.IInquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@Autowired
	IInquiryService inquiryService;
	
	//문의글 삽입
	@PostMapping("/insert")
	public String insertInquiry(int productId, int productGroupId, String size, String color, String other, String content) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;

		int customerId = Integer.valueOf(userDetails.getUsername());
		
		inquiryService.insertInquiry(productGroupId, customerId, size, color, other, content);
		
		return "redirect:/product/" + productId + "/" + productGroupId;
	}
}
