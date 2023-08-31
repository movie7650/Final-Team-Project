package com.example.daitso.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.service.IReviewService;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	IProductService productService;

	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	IReviewService reviewService;
	
	@GetMapping("")
	public String main() {
		return "/main/main";
	}
	
	//상품 정보 조회하기(리뷰, 문의글 포함)
	@GetMapping("/{productId}")
	public String selectProduct(@PathVariable int productId, Model model) {
		Product product = productService.selectProduct(productId);
		List<Review> rList = reviewService.selectProductReview(productId);
		int reviewAvg = reviewService.selectProductReviewAvg(productId);
		List<String> oListFirst = productService.selectProductOptionFirst(product.getProductNm());
		
		String pOptionFirst = "0";
		String pOptionSecond = "0";
		if(product.getProductOptionFirst() != null) {
			pOptionFirst = product.getProductOptionFirst();
		}
		if(product.getProductOptionSecond() != null) {
			pOptionSecond = product.getProductOptionSecond();
		}
		System.out.println(pOptionFirst);
		System.out.println(pOptionSecond);
		//List<String> oListSecond = productService.selectProductOptionSecond(product.getProductNm(), pOptionFirst);
		//List<String> oListThird = productService.selectProductOptionThird(product.getProductNm(), pOptionFirst, pOptionSecond);
		
		//System.out.println(oListSecond);
		//System.out.println(oListThird);
		
		model.addAttribute("product", product);
		model.addAttribute("rList", rList);
		model.addAttribute("reviewAvg", reviewAvg);
		model.addAttribute("rCnt", rList.size());
		model.addAttribute("oListFirst", oListFirst);
		
		return "/main/productDetail";
	}

}
