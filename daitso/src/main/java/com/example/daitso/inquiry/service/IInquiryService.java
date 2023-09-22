package com.example.daitso.inquiry.service;

import java.util.List;

import com.example.daitso.inquiry.model.InquiryInfo;
import com.example.daitso.inquiry.model.InquiryInfoWithAnswer;
import com.example.daitso.inquiry.model.InquiryProduct;
import com.example.daitso.inquiry.model.InquirySelect;
import com.example.daitso.inquiry.model.MyInquirySelect;

public interface IInquiryService {
	
	//문의글 삽입
	void insertInquiry(int productGroupId, int cId ,String size, String color, String other, String content);
	
	void selectInquiry(int inquiryId);
	
	//특정 상품에 해당하는 문의글 조회
	List<InquiryProduct> selectProductInquiry(int productGroupId);

	// 문의 답변 상태별 총 문의 개수 조회
	int selectTotalInquiryCountByInquiryAnsDv(char inquiryAnsDv);

	// 페이징 처리 -> 문의 답변 상태별 총 문의 리스트 조회
	List<InquirySelect> selectInquiryListByInquiryAnsDv(char inquiryAnsDv, int page);
	
	// 문의 아이디로 문의 내용 조회
	InquiryInfo selectInquiryInfoByInquiryId(int inquiryId);

	// 문의 답변하기
	void insertInquiryComplete(int inquiryId, int productId, String inquiryContent);

	// 문의 아이디로 문의 내용 조회 + 문의 답변 보기
	InquiryInfoWithAnswer selectInquiryInfoWithAnswerByInquiryId(int inquiryId);
	
	//내 문의글 조회 
	List<MyInquirySelect> selectMyInquiry(int customerId);
	
	//내 문의글 삭제
	void deleteMyInquiry(MyInquirySelect myInquirySelect);
	
	//내 InquiryContent 갖고오기
	String selectInquiryContent(int customerId);

}
