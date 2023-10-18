package com.example.daitso.inquiry.service;

import com.example.daitso.inquiry.model.InquiryInsertDTO;
import com.example.daitso.inquiry.model.MyInquirySelect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class InquiryServiceTest {

    @Autowired
    InquiryService inquiryService;

    @Test
    @Transactional
    void 문의글삽입() {
        //given
    	List<MyInquirySelect> myInquiryListBefore = inquiryService.selectMyInquiry(17);
        InquiryInsertDTO inquiryInsertDTO = InquiryInsertDTO.builder()
                .productGroupId(5)
                .size("1kg")
                .color("색상옵션 없음")
                .other("기타옵션 없음")
                .customerId(17)
                .content("너드 남")
                .build();

        // when
        inquiryService.insertInquiry(inquiryInsertDTO);
        
        // then
        List<MyInquirySelect> myInquiryListAfter = inquiryService.selectMyInquiry(17);
        assertThat(myInquiryListBefore.size()+1).isEqualTo(myInquiryListAfter.size());
    }
}