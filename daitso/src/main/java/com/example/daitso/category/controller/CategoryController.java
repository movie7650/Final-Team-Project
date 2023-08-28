package com.example.daitso.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	IProductService productService;
	
	// header에 상위 카테고리 띄우기
	@GetMapping("/getAllFirstCategoryIdAndName")
	public @ResponseBody List<Category> getAllFirstCategoryIdAndName() {
		return categoryService.getAllFirstCategoryIdAndName();	
	}
	
	// header에 하위 카테고리 띄우기
	@GetMapping("/getSecondCategoryIdAndNameByFirstCategoryId/{categoryId}")
	public @ResponseBody List<Category> getSecondCategoryIdAndNameByFirstCategoryId(@PathVariable int categoryId) {
		return categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);	
	}
	
	@GetMapping("/{categoryId}")
	public String productList(@PathVariable int categoryId, Model model) {
		List<Product> list = productService.selectProductList(categoryId);
		List<Category> categoryList = categoryService.selectCategoryList(categoryId);
		String path = categoryService.selectCategoryPath(categoryId);
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("productList", list);
		model.addAttribute("path", path);
		
		return "/main/product";
	}
}
