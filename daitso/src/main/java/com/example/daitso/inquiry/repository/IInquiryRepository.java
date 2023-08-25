package com.example.daitso.inquiry.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.inquiry.model.Inquiry;

@Repository
@Mapper
public interface IInquiryRepository {
	void insertInquiry(Inquiry inquiry);
	void selectInquiry(int inquiryId);
	
	
	

}
