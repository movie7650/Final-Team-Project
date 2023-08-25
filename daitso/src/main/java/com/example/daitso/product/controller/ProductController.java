package com.example.daitso.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	IProductService productService;

	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("")
	public String main() {
		return "/main/main";
	}
	
	
	@GetMapping("/admin")
	public String selectAllProducts(Model model) {
		List<Product> products = productService.selectAllProducts();
		model.addAttribute("products",products);
		return "admin/product/admin-product";
	}
	
	@GetMapping("/admin/register")
	public String addForm(Model model) {
		List<Category> categories = categoryService.getAllFirstCategoryIdAndName();
		model.addAttribute("categories",categories);
		List<Product> products = productService.selectAllProducts();
		model.addAttribute("products",products);
		return "admin/product/admin-register";
	}

	@PostMapping("/admin/register")
	public String registerProducts(@RequestParam int productId, @RequestParam String productCode,Product product) {
		productService.registerProducts(product);
		productService.changeProductCode(productId, productCode);
		return "redirect:/product/admin";
	}
	
	@GetMapping("/subCategories/{categoryId}")
    @ResponseBody
    public List<Category> getSubCategories(@PathVariable int categoryId) {
        List<Category> subCategories = categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
        return subCategories;
    }

	@GetMapping("/{categoryId}")
	public String selectProduct(@PathVariable int categoryId, Model model) {
		Product product = productService.selectProduct(categoryId);
		System.out.println(product);
		return "/main/productDetail";
	}

}
