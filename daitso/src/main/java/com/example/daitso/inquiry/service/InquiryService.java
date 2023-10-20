package com.example.daitso.inquiry.service;

import java.util.List;

import com.example.daitso.inquiry.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.inquiry.repository.IInquiryRepository;
import com.example.daitso.product.repository.IProductRepository;

@Service
public class InquiryService implements IInquiryService {
	
	@Autowired
	IInquiryRepository inquiryRepository;
	
	@Autowired
	IProductRepository productRepository;

	@Transactional
	public int insertInquiry(InquiryInsertDTO inquiryInsertDTO) {
		int pId = productRepository.selectInquiryProductId(inquiryInsertDTO.getProductGroupId(), inquiryInsertDTO.getSize(), inquiryInsertDTO.getColor(), inquiryInsertDTO.getOther());
		if(pId != 0) {
			return inquiryRepository.insertInquiry(pId, inquiryInsertDTO.getCustomerId(), inquiryInsertDTO.getContent());
		}else {
			return 0;
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

	// 문의 답변 상태별 총 문의 개수 조회
	@Override
	public int selectTotalInquiryCountByInquiryAnsDv(char inquiryAnsDv) {
		return inquiryRepository.selectTotalInquiryCountByInquiryAnsDv(inquiryAnsDv);
	}

	// 페이징 처리 -> 문의 답변 상태별 총 문의 리스트 조회
	@Override
	public List<InquirySelect> selectInquiryListByInquiryAnsDv(char inquiryAnsDv, int page) {
		int start = (page-1)*10 + 1;
		return inquiryRepository.selectInquiryListByCategory(inquiryAnsDv, start, start+9);
	}

	// 문의 아이디로 문의 내용 조회
	@Override
	public InquiryInfo selectInquiryInfoByInquiryId(int inquiryId) {
		return inquiryRepository.selectInquiryInfoByInquiryId(inquiryId);
	}

	// 문의 답변하기
	@Transactional
	public void insertInquiryComplete(int inquiryId, int productId, String inquiryContent) {
		inquiryRepository.insertInquiryComplete(inquiryId, productId, inquiryContent);
		inquiryRepository.updateInquiryAnsDv(inquiryId);
	}

	// 문의 아이디로 문의 내용 조회 + 문의 답변 보기
	@Override
	public InquiryInfoWithAnswer selectInquiryInfoWithAnswerByInquiryId(int inquiryId) {
		return inquiryRepository.selectInquiryInfoWithAnswerByInquiryId(inquiryId);
	}
	//내가 작성한 문의글 조회
	@Override
	public List<MyInquirySelect> selectMyInquiry(int customerId, int page) {
		int start = (page-1)*10 + 1;
		return inquiryRepository.selectMyInquiry(customerId,start,start+9);
	}
	
	//내 문의글 삭제
	@Override
	public void deleteMyInquiry(int inquiryId) {
		inquiryRepository.deleteMyInquiry(inquiryId);		
	}
	//내문의 status가 Y 인 개수
	@Override
	public int countInquiryStatusY(int customerId) {
		return inquiryRepository.countInquiryStatusY(customerId);
	}
	// inquiryId값에 따른 문의내역과 문의답변 가져오기
	@Override
	public String selectInquiryContent(int inquiryId) {
		return inquiryRepository.selectInquiryContent(inquiryId);
	}

	//문의답변ID에 따른 문의 답변 가져오기
	@Override
	public String selectMyInquiryPRIdInquiry(int inquiryId) {
		return inquiryRepository.selectMyInquiryPRIdInquiry(inquiryId);
	}
	
	//내문의 작성시간
	@Override
	public String selectMyInquiryTime(int inquiryId) {
		return inquiryRepository.selectMyInquiryTime(inquiryId);
	}
	//내문의의 답변 작성시간
	@Override
	public String selectMyInquiryReplyTime(int inquiryId) {
		return inquiryRepository.selectMyInquiryReplyTime(inquiryId);
	}

	// 문의 답변 삭제하기(관리자)
	@Transactional
	public void deleteInquiryAdmin(int inquiryId, int ansInquiryId) {
		inquiryRepository.deleteInquiryAdmin(ansInquiryId);
		inquiryRepository.updateInquiryAdminDelete(inquiryId);
	}
	
	// 검색 -> 페이징 처리 -> 문의 답변 상태별 총 문의 리스트 조회
	@Override
	public List<InquirySelect> selectInquiryListByInquiryAnsDvAndSearch(char inquiryAnsDv, String searchFilter,
			String search, int page) {
		int start = (page-1)*10 + 1;
		return inquiryRepository.selectInquiryListByCategoryAndSearch(inquiryAnsDv, searchFilter, search, start, start + 9);
	}

}
