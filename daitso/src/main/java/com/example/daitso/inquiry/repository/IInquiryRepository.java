package com.example.daitso.inquiry.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.inquiry.model.InquiryInfo;
import com.example.daitso.inquiry.model.InquiryInfoWithAnswer;
import com.example.daitso.inquiry.model.InquiryProduct;
import com.example.daitso.inquiry.model.InquirySelect;
import com.example.daitso.inquiry.model.MyInquirySelect;

@Repository
@Mapper
public interface IInquiryRepository {
	
	//문의글 삽입
	int insertInquiry(@Param("pId") int pId, @Param("cId") int cId, @Param("content") String content);
	
	void selectInquiry(int inquiryId);
	
	//특정 상품에 해당하는 문의글 조회
	List<InquiryProduct> selectProductInquiry(int productGroupId);

	// 문의 답변 상태별 총 문의 개수 조회
	int selectTotalInquiryCountByInquiryAnsDv(char inquiryAnsDv);

	// 페이징 처리 -> 문의 답변 상태별 총 문의 리스트 조회 
	List<InquirySelect> selectInquiryListByCategory(@Param("inquiryAnsDv") char inquiryAnsDv, @Param("start") int start, @Param("end") int end);

	// 문의 아이디로 문의 내용 조회
	InquiryInfo selectInquiryInfoByInquiryId(int inquiryId);

	// 문의 답변하기
	void insertInquiryComplete(@Param("inquiryId") int inquiryId, @Param("productId") int productId, @Param("inquiryContent") String inquiryContent);

	// 문의 답변 후 문의 상태 W -> C 로 변경
	void updateInquiryAnsDv(int inquiryId);
	
	// 문의 아이디로 문의 내용 조회 + 문의 답변 보기
	InquiryInfoWithAnswer selectInquiryInfoWithAnswerByInquiryId(int inquiryId);
	
	//내 문의글 조회 
	List<MyInquirySelect> selectMyInquiry(int customerId);
	
	//내 문의글 삭제
	void deleteMyInquiry(MyInquirySelect myInquirySelect);
}
