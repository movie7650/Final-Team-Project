package com.example.daitso.category.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.example.daitso.category.model.Category;

@Repository
@Mapper
public interface ICategoryRepository {
	
	// 상위 카테고리(식품,도서,...) 고유번호와 이름 갖고오기
	List<Category> getAllFirstCategoryIdAndName();
	
	// 상위 카테고리(식품,도서,...) 고유번호로 부터 하위 카테고리 고유번호와 이름 갖고오기
	List<Category> getSecondCategoryIdAndNameByFirstCategoryId(int categoryId);
	
	// 선택된 카테고리를 기준으로 하위 카테고리들을 계층형으로 갖고오기
	List<Category> selectCategoryList(@Param("categoryId") int categoryId);
	
	// 선택된 카테고리의 경로 갖고오기
	String selectCategoryPath(@Param("categoryId") int categoryId);
	
	// 전체 카테고리를 계층형으로 갖고오기
	List<Category> selectAllCategory();
}

