package com.example.daitso.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.inquiry.model.InquiryInsertDTO;
import com.example.daitso.inquiry.service.IInquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@Autowired
	IInquiryService inquiryService;
	
	//문의글 삽입
	@PostMapping("/insert")
	@ResponseBody
	public int insertInquiry(@RequestBody InquiryInsertDTO inquiryInsertDTO ) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		
		int customerId = Integer.valueOf(userDetails.getUsername());
		
		return inquiryService.insertInquiry(inquiryInsertDTO.getProductGroupId(), customerId, inquiryInsertDTO.getSize(), inquiryInsertDTO.getColor(), inquiryInsertDTO.getOther(), inquiryInsertDTO.getContent());
	}
}
