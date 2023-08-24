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
	
	@Override
	public List<Category> selectAllCategories() {
		return categoryRepository.selectAllCategories();
	}

}
