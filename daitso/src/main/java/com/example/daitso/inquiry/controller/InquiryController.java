package com.example.daitso.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.inquiry.service.IInquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@Autowired
	IInquiryService inquiryService;
	
	@PostMapping("/insert")
	public void insertInquiry(int productGroupId, String size, String color, String other, String content) {
		System.out.println(productGroupId + size + color + other + content);
		
	}
}
