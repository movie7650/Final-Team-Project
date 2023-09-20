package com.example.daitso.inquiry.service;

import java.util.List;

import com.example.daitso.inquiry.model.Inquiry;
import com.example.daitso.inquiry.model.InquiryProduct;

public interface IInquiryService {
	
	//문의글 삽입
	void insertInquiry(int productGroupId, int cId ,String size, String color, String other, String content);
	
	void selectInquiry(int inquiryId);
	
	//특정 상품에 해당하는 문의글 조회
	List<InquiryProduct> selectProductInquiry(int productGroupId);
	

}
