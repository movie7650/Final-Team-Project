package com.example.daitso.category.sevice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.daitso.category.model.Category;

public interface ICategoryService {
	
	// 상위 카테고리(식품,도서,...) 고유번호와 이름 갖고오기
	List<Category> getAllFirstCategoryIdAndName();
	
	// 상위 카테고리(식품,도서,...) 고유번호로 부터 하위 카테고리 고유번호와 이름 갖고오기
	List<Category> getSecondCategoryIdAndNameByFirstCategoryId(int categoryId);
	
	// 선택된 카테고리를 기준으로 하위 카테고리들을 계층형으로 갖고오기
	List<Category> selectCategoryList(int categoryid);
	
	// 선택된 카테고리의 경로 갖고오기
	String selectCategoryPath(int categoryId);
	
	// 전체 카테고리를 계층형으로 갖고오기
	List<Category> selectAllCategory();
	
	// 카테고리 계층 업데이트 
	void updateCategory(int categoryId, int parentCategoryId);
	
	// 카테고리 무작휘 8개 갖고오기
	List<Category> selectMainCategory();
}
