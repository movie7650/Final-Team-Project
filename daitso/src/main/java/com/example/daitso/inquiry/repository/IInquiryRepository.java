package com.example.daitso.inquiry.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.inquiry.model.InquiryProduct;

@Repository
@Mapper
public interface IInquiryRepository {
	
	//문의글 삽입
	void insertInquiry(@Param("pId") int pId, @Param("cId") int cId, @Param("content") String content);
	
	void selectInquiry(int inquiryId);
	
	//특정 상품에 해당하는 문의글 조회
	List<InquiryProduct> selectProductInquiry(int productGroupId);

}
