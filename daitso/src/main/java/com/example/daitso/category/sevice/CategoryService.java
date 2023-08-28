package com.example.daitso.category.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.repository.ICategoryRepository;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	ICategoryRepository categoryRepository;
	
	// 상위 카테고리(식품,도서,...) 고유번호와 이름 갖고오기
	@Override
	public List<Category> getAllFirstCategoryIdAndName() {
		return categoryRepository.getAllFirstCategoryIdAndName();
	}

	// 상위 카테고리(식품,도서,...) 고유번호로 부터 하위 카테고리 고유번호와 이름 갖고오기
	@Override
	public List<Category> getSecondCategoryIdAndNameByFirstCategoryId(int categoryId) {
		return categoryRepository.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
	}
	
	@Override
	public List<Category> selectCategoryList(int categoryid) {
		return categoryRepository.selectCategoryList(categoryid);
	}

	@Override
	public String selectCategoryPath(int categoryId) {
		return categoryRepository.selectCategoryPath(categoryId);
	}

}
