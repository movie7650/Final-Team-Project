package com.example.daitso.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("/{categoryId}")
	public String productList(@PathVariable int categoryId, Model model) {
		List<Product> list = productService.selectProductList(categoryId);
		List<Category> categoryList = categoryService.selectCategoryList(categoryId);
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("productList", list);
		
		return "/main/product";
	}
}
