package com.example.daitso.inquiry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.inquiry.model.Inquiry;
import com.example.daitso.inquiry.model.InquiryProduct;
import com.example.daitso.inquiry.repository.IInquiryRepository;
import com.example.daitso.product.repository.IProductRepository;

@Service
public class InquiryService implements IInquiryService {
	
	@Autowired
	IInquiryRepository inquiryRepository;
	
	@Autowired
	IProductRepository productRepository;

	@Transactional
	public void insertInquiry(int productGroupId, int cId, String size, String color, String other, String content) {
		System.out.println("im here" + productGroupId + size + color + other);
		int pId = productRepository.selectInquiryProductId(productGroupId, size, color, other);
		if(pId != 0) {
			inquiryRepository.insertInquiry(pId, cId ,content);
		}
	}

	@Override
	public void selectInquiry(int inquiryId) {
		inquiryRepository.selectInquiry(inquiryId);		
	}

	@Override
	public List<InquiryProduct> selectProductInquiry(int productGroupId) {
		return inquiryRepository.selectProductInquiry(productGroupId);
	}

}
