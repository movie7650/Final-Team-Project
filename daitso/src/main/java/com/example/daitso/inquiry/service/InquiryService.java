package com.example.daitso.inquiry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.inquiry.model.Inquiry;
import com.example.daitso.inquiry.model.InquiryInfo;
import com.example.daitso.inquiry.model.InquiryInfoWithAnswer;
import com.example.daitso.inquiry.model.InquiryProduct;
import com.example.daitso.inquiry.model.InquirySelect;
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

}
