package com.example.daitso.inquiry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.inquiry.model.Inquiry;
import com.example.daitso.inquiry.repository.IInquiryRepository;

@Service
public class InquiryService implements IInquiryService {
	
	@Autowired
	IInquiryRepository inquiryRepository;

	@Override
	public void insertInquiry(Inquiry inquiry) {
		inquiryRepository.insertInquiry(inquiry);
		
	}

	@Override
	public void selectInquiry(int inquiryId) {
		inquiryRepository.selectInquiry(inquiryId);		
	}

}
