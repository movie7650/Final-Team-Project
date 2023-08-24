package com.example.daitso.category.sevice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.daitso.category.model.Category;

public interface ICategoryService {
	
	// 상위 카테고리(식품,도서,...) 고유번호와 이름 갖고오기
	List<Category> getAllFirstCategoryIdAndName();
	
	// 상위 카테고리(식품,도서,...) 고유번호로 부터 하위 카테고리 고유번호와 이름 갖고오기
	List<Category> getSecondCategoryIdAndNameByFirstCategoryId(int categoryId);
}
