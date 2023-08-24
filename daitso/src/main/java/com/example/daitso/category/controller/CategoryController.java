package com.example.daitso.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;


@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("/admin")
	public String selectAllCategories(Model model) {
		List<Category> categories = categoryService.selectAllCategories();
		model.addAttribute("categories",categories);
		return "admin/product/test3";
	}
}
