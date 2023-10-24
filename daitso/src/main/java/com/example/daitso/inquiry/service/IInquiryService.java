package com.example.daitso.inquiry.service;

import java.util.List;

import com.example.daitso.inquiry.model.*;
import org.apache.ibatis.annotations.Param;

public interface IInquiryService {
	
	//문의글 삽입
	int insertInquiry(InquiryInsertDTO inquiryInsertDTO);
	
	//내 문의 작성시간 
	String selectMyInquiryTime(@Param("inquiryId") int inquiryId);
	
	//내 문의의 답변 작성시간
	String selectMyInquiryReplyTime(@Param("inquiryId") int inquiryId);
	
	void selectInquiry(int inquiryId);
	
	//특정 상품에 해당하는 문의글 조회
	List<InquiryProduct> selectProductInquiry(int productGroupId);

	// 문의 답변 상태별 총 문의 개수 조회
	int selectTotalInquiryCountByInquiryAnsDv(char inquiryAnsDv);

	// 페이징 처리 -> 문의 답변 상태별 총 문의 리스트 조회
	List<InquirySelect> selectInquiryListByInquiryAnsDv(char inquiryAnsDv, int page);
	
	// 검색 -> 페이징 처리 -> 문의 답변 상태별 총 문의 리스트 조회
	List<InquirySelect> selectInquiryListByInquiryAnsDvAndSearch(char inquiryAnsDv, String searchFilter, String search, int page);
	
	// 문의 아이디로 문의 내용 조회
	InquiryInfo selectInquiryInfoByInquiryId(int inquiryId);

	// 문의 답변하기
	void insertInquiryComplete(int inquiryId, int productId, String inquiryContent);

	// 문의 아이디로 문의 내용 조회 + 문의 답변 보기
	InquiryInfoWithAnswer selectInquiryInfoWithAnswerByInquiryId(int inquiryId);
	
	//내 문의글 답변대기 조회
	List<MyInquirySelect> selectMyInquiryReplyWaiting(int customerId,int page);
	
	//inquiry status가  Y 인 답변대기 갯수
	int countInquiryReplyWaitingStatusY(int customerId);
	
	//내 문의글 - 답변완료 조회
	List<MyInquirySelect> selectMyInquiryReplyCompleted(int customerId, int page);
	
	//inquiry status가 Y인 답변완료 갯수
	int countInquiryReplyCompletedStatusY(int customerId);
	
	//내 문의글 삭제
	void deleteMyInquiry(@Param("inquiryId")int inquiryId);
	
	
	//문의내용 선택하기
	String selectInquiryContent(int inquiryId);
	
	//문의답변ID에 따른 문의 답변내용가져오기
	String selectMyInquiryPRIdInquiry(@Param("inquiryId") int inquiryId);

	// 문의 답변 삭제하기(관리자)
	void deleteInquiryAdmin(int inquiryId, int ansInquiryId);

}
