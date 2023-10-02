package com.example.daitso.hello.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.config.InfiniteStreamRecognize;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.SpecialProduct;
import com.example.daitso.product.service.IProductService;

@Controller
@RequestMapping("/")
public class HelloController {
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ICategoryService categoryService;
	
	//메인 페이지
	@GetMapping("")
	public String hello(Model model) {
		List<SpecialProduct> list = productService.selectSpecialProduct();
		List<Product> sList = productService.saleProductList();
		List<Category> cList = categoryService.selectMainCategory();
		model.addAttribute("productList", list);
		model.addAttribute("saleProductList", sList);
		model.addAttribute("categoryList", cList);

		return "/main/main";
	}
	
	@GetMapping("voice")
	@ResponseBody
	public Map<String, String> stt(@RequestParam(value="selector", required = false, defaultValue="0") int selector) {
		Map<String, String> response = new HashMap<>();
		if(selector == 1) {
			InfiniteStreamRecognize.doThatShit();
			return response;
		}else {
			response.put("text", InfiniteStreamRecognize.doThat(null));
			return response;			
		}
	}

}

