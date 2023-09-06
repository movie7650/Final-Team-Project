package com.example.daitso.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductOption;
import com.example.daitso.product.service.IProductService;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.model.ReviewProductDetail;
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
	@GetMapping("/{productId}/{groupId}")
	public String selectProduct(@PathVariable int productId, @PathVariable int groupId, Model model) {
		Product product = productService.selectProduct(productId);
		
		List<ReviewProductDetail> rList = reviewService.selectProductReview(groupId, 1);

		int reviewAvg = reviewService.selectProductReviewAvg(groupId);
		int reviewCnt = reviewService.selectProductReviewCount(groupId);
		
		int totalPage = 0;
		if(reviewCnt > 0) {
			totalPage = (int)Math.ceil(reviewCnt/2.0);
		}
		int totalPageBlock = (int)(Math.ceil(totalPage/10.0));
		//리뷰 정보는 비동기로 보여줄 것이기 때문에 여기서 nowPageBlock은 무조건 1
		int nowPageBlock = 1;
		int startPage = (nowPageBlock-1)*10 + 1;
		int endPage = 0;
		if(totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		}else {
			endPage = totalPage;
		}
		
		List<String> oListFirst = productService.selectProductOptionFirst(product.getProductGroupId());

		String pOptionFirst = "0";
		String pOptionSecond = "0";
		if(product.getProductOptionFirst() != null) {
			pOptionFirst = product.getProductOptionFirst();
		}
		if(product.getProductOptionSecond() != null) {
			pOptionSecond = product.getProductOptionSecond();
		}
		
		List<String> oListSecond = productService.selectProductOptionSecond(product.getProductGroupId(), pOptionFirst);
		List<String> oListThird = productService.selectProductOptionThird(product.getProductGroupId(), pOptionFirst, pOptionSecond);
		
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", 1);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("product", product);
		model.addAttribute("rList", rList);
		model.addAttribute("reviewAvg", reviewAvg);
		model.addAttribute("rCnt", reviewCnt);
		model.addAttribute("oListFirst", oListFirst);
		model.addAttribute("oListSecond", oListSecond);
		model.addAttribute("oListThird", oListThird);
		
		return "/main/productDetail";
	}
	
	//상품 옵션 변경하기
	@GetMapping("/change/{productGroupId}")
	@ResponseBody
	public Product selectChangeProduct(@PathVariable int productGroupId, @RequestParam(value="optionFirst", required = false, defaultValue="0") String optionFirst
			,@RequestParam(value="optionSecond", required = false, defaultValue="0") String optionSecond
			,@RequestParam(value="optionThird", required = false, defaultValue="0") String optionThird) {
		//System.out.println(productNm + " "   + optionFirst +  " " + optionSecond + " " + optionThird);
		return productService.selectOptionProduct(productGroupId, optionFirst, optionSecond, optionThird);
	}
	
	//옵션 리스트 변경하기
	@GetMapping("/option/{productGroupId}")
	@ResponseBody
	public List<List> selectChangeOption(@PathVariable int productGroupId, @RequestParam(value="optionFirst", required = false, defaultValue="0") String optionFirst
			,@RequestParam(value="optionSecond", required = false, defaultValue="0") String optionSecond
			,@RequestParam(value="optionThird", required = false, defaultValue="0") String optionThird){
		//System.out.println("im here" + productNm + " "   + optionFirst +  " " + optionSecond + " " + optionThird);
		List<List> list = new ArrayList<>();
		List<String> oListSecond = productService.selectProductOptionSecond(productGroupId, optionFirst);
		List<String> oListThird = productService.selectProductOptionThird(productGroupId, optionFirst, optionSecond);
		list.add(oListSecond);
		list.add(oListThird);
		//System.out.println(list);
		
		return list;
	}
	
	//문의하기 상품 옵션 변경하기
	@GetMapping("/inquiry/option")
	@ResponseBody
	public List<List> selectInquiryProductChangeOption(@RequestParam(value="productGroupId") int productGroupId, @RequestParam(value="optionFirst") String optionFirst
			,@RequestParam(value="optionSecond", required = false, defaultValue="0") String optionSecond
			,@RequestParam(value="optionThird", required = false, defaultValue="0") String optionThird){
		List<List> list = new ArrayList<>();
		if(optionSecond.equals("0")) {
			list.add(productService.selectProductOptionSecond(productGroupId, optionFirst));
			list.add(productService.selectProductOptionThird(productGroupId, optionFirst, optionSecond));			
		}else {
			list.add(productService.selectProductOptionThird(productGroupId, optionFirst, optionSecond));						
		}
		return list;
	}
}
