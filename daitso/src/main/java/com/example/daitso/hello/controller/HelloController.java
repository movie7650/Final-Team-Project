package com.example.daitso.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.SpecialProduct;
import com.example.daitso.product.service.IProductService;

@Controller
@RequestMapping("/")
public class HelloController {
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("")
	public String hello(Model model) {
		List<SpecialProduct> list = productService.selectSpecialProduct();
		List<Category> cList = categoryService.selectMainCategory();
		model.addAttribute("productList", list);
		model.addAttribute("categoryList", cList);
		return "/main/main";
	}
}
