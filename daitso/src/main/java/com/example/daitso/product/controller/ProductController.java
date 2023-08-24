package com.example.daitso.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	IProductService productService;

	
	@GetMapping("")
	public String main() {
		return "/main/main";
	}
	public String selectAllProducts(Model model) {
		List<Product> products = productService.selectAllProducts();
		model.addAttribute("products",products);
		return "admin/product/admin-product";
	}
	
	@GetMapping("/admin/register")
	public String addForm() {
		return "admin/product/admin-register";
//		return "admin/product/test3";
	}

	@PostMapping("/admin/register")
	public String registerProducts(Product product) {
		productService.registerProducts(product);
		return "redirect:/product/admin";
	}
	
	@GetMapping("/{categoryId}")
	public String selectProduct(@PathVariable int categoryId, Model model) {
		Product product = productService.selectProduct(categoryId);
		System.out.println(product);
		return "/main/productDetail";
	}
}
