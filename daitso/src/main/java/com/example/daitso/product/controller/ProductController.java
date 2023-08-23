package com.example.daitso.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	IProductService productService;
	
	@GetMapping("/{categoryId}")
	public String productList(@PathVariable int categoryId) {
		System.out.println(categoryId);
		List<Product> list = productService.selectProductList(categoryId);
		
		return "/main/product";
	}
	
	@GetMapping("")
	public String main() {
		return "/main/main";
	}
	
	@GetMapping("/admin-product")
	public String selectAllProducts(Model model) {
		List<Product> products = productService.selectAllProducts();
		model.addAttribute("products",products);
		return "admin/product/admin-product";
	}
	
}
