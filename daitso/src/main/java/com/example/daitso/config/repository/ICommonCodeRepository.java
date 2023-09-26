package com.example.daitso.config.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.config.CommonCode;



@Repository
@Mapper
public interface ICommonCodeRepository {
	
	// 최상위 공통코드 조회하기
	List<CommonCode> selectAllCommonCodesPr(@Param("offset") int offset, @Param("pageSize") int pageSize);
		
	// 최상위 공통코드 개수 조회하기
	int selectCountCommonCodesPr();
	
    // 최상위 공통코드 삭제하기
    void deleteCommonCodePr(int commonCodeId);
    
	// 공통코드 조회하기(최상위 공통코드별)
    List<CommonCode> selectCommonCodesByPr(int commonCodeId);
    
    // commonCodeId로 공통코드 정보 조회하기
    CommonCode selectCommonCode(int commonCodeId);
    
    // 공통코드 수정하기
 	void updateCommonCode(CommonCode commonCode);
    
 	// 공통코드 삭제하기
 	void deleteCommonCode(int commonCodeId);
 	
 	void registerCommonCodes(CommonCode commonCode);
}
