package com.example.daitso.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	IProductService productService;
	
	// header에 상위 카테고리 띄우기
	@GetMapping("/first-category")
	public @ResponseBody List<Category> getAllFirstCategoryIdAndName() {
		return categoryService.getAllFirstCategoryIdAndName();	
	}
	
	// header에 하위 카테고리 띄우기
	@GetMapping("/second-category/{categoryId}")
	public @ResponseBody List<Category> getSecondCategoryIdAndNameByFirstCategoryId(@PathVariable int categoryId) {
		return categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);	
	}
	
	// category별 상품 조회 1페이지
	@GetMapping("/{categoryId}")
	public String productList(@PathVariable int categoryId, @RequestParam(value="sort", required=false, defaultValue="normal") String sort, HttpSession session ,Model model) {
		
		return productList(categoryId, 1, session, model, sort);
	}
	
	// category별 상품 조회 2~페이지
	@GetMapping("/{categoryId}/{page}")
	public String productList(@PathVariable int categoryId, @PathVariable int page ,HttpSession session ,Model model, String sort) {
		session.setAttribute("page", page);
		model.addAttribute("categoryId", categoryId);
		List<Product> list = productService.selectProductList(categoryId, page, sort);
		List<Category> categoryList = categoryService.selectCategoryList(categoryId);
		String path = categoryService.selectCategoryPath(categoryId);
		
		int listCnt = productService.selectCountProductList(categoryId);
		int totalPage = 0;
		if(listCnt > 0) {
			totalPage = (int)Math.ceil(listCnt/16.0);
		}
		
		int totalPageBlock = (int)(Math.ceil(totalPage/10.0));
		int nowPageBlock = (int)(Math.ceil(page/10.0));
		int startPage = (nowPageBlock-1)*10 + 1;
		int endPage = 0;
		if(totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		}else {
			endPage = totalPage;
		}
		
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("productList", list);
		model.addAttribute("path", path);
		
		return "/main/product";
	}

}
