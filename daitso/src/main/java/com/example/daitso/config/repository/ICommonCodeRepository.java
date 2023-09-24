package com.example.daitso.config.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.config.CommonCode;

@Repository
@Mapper
public interface ICommonCodeRepository {
	
	// 전체 공통코드 조회하기
	List<CommonCode> selectAllCommonCodes(@Param("offset") int offset, @Param("pageSize") int pageSize);
		
	// 전체 공통코드 개수 조회하기
	int selectCountCommonCodes();
}
