package com.example.daitso.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("/getAllFirstCategoryIdAndName")
	public @ResponseBody List<Category> getAllFirstCategoryIdAndName() {
		return categoryService.getAllFirstCategoryIdAndName();	
	}
	
	@GetMapping("/getSecondCategoryIdAndNameByFirstCategoryId/{categoryId}")
	public @ResponseBody List<Category> getSecondCategoryIdAndNameByFirstCategoryId(@PathVariable int categoryId) {
		return categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);	
	}
}
