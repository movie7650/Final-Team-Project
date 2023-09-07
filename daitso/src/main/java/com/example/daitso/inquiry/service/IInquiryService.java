package com.example.daitso.inquiry.service;

import java.util.List;

import com.example.daitso.inquiry.model.Inquiry;
import com.example.daitso.inquiry.model.InquiryProduct;

public interface IInquiryService {
	
	void insertInquiry(int productGroupId, int cId ,String size, String color, String other, String content);
	
	void selectInquiry(int inquiryId);
	
	List<InquiryProduct> selectProductInquiry(int productGroupId);
	

}
