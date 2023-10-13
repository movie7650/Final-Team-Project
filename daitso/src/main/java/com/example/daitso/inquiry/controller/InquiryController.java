package com.example.daitso.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.check.ILogincheckService;
import com.example.daitso.inquiry.model.InquiryInsertDTO;
import com.example.daitso.inquiry.service.IInquiryService;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@Autowired
	IInquiryService inquiryService;
	
	@Autowired
	ILogincheckService logincheckService;
	
	//문의글 삽입
	@PostMapping("/insert")
	@ResponseBody
	public int insertInquiry(@RequestBody InquiryInsertDTO inquiryInsertDTO ) {
		System.out.println("why");
		System.out.println(HtmlUtils.htmlEscape(inquiryInsertDTO.getContent()));
		// spring security -> 사용자 고유번호 받아오기
		int customerId = logincheckService.loginCheck();
		
		return inquiryService.insertInquiry(inquiryInsertDTO.getProductGroupId(), customerId, inquiryInsertDTO.getSize(), inquiryInsertDTO.getColor(), inquiryInsertDTO.getOther(), inquiryInsertDTO.getContent());
	}
}
