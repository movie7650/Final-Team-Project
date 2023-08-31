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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;
import com.example.daitso.review.model.Review;
import com.example.daitso.review.service.IReviewService;

import jakarta.servlet.http.HttpSession;


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
	
	//전체 상품 조회하기
	@GetMapping("/admin")
	public String selectAllProducts(Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int pageSize
	) {
	    int totalCount = productService.getTotalProductCount();  //총 상품 갯수
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    
	    // 페이지 번호를 범위 내로 제한
	    if (page < 1) {
	        page = 1;
	    } else if (page > totalPages) {
	        page = totalPages;
	    }

	    int startRow = (page - 1) * pageSize + 1;
	    int endRow = startRow + pageSize - 1;

	    List<Product> products = productService.selectPagedProducts(startRow, endRow);
	    List<Category> categories = categoryService.getAllFirstCategoryIdAndName();

	    model.addAttribute("products", products);
	    model.addAttribute("categories", categories);

	    model.addAttribute("currentPage", page);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("totalPages", totalPages);

	    return "admin/product/admin-product";
	}	
	
	//상품 등록하기
	@PostMapping("/admin")
	public String registerProducts(Product product, Model model, @RequestPart MultipartFile file) {
		productService.registerProducts(product);
		System.out.println(file.getOriginalFilename());
		System.out.println(product.getProductImageFirst());
		model.addAttribute("message","상품이 등록되었습니다.");
	    model.addAttribute("searchUrl","/product/admin");
	    return "admin/product/message";
	}
	
	//하위 카테고리 불러오기
	@GetMapping("/sub-categories/{categoryId}")
    @ResponseBody
    public List<Category> getSubCategories(@PathVariable int categoryId) {
        List<Category> subCategories = categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
        return subCategories;
    }
	
	//상품 삭제하기
	@PostMapping("/admin/delete")
    public String deleteProduct(@RequestParam int productId, Model model) {
	    productService.deleteProduct(productId);
	    model.addAttribute("message","상품이 삭제되었습니다.");
	    model.addAttribute("searchUrl","/product/admin");
	    return "admin/product/message";
    }
	
	//상품 수정을 위해 해당 상품 정보 불러오기
	@GetMapping("/admin/update/{productId}")
	@ResponseBody
	public Product selectProductId(@PathVariable int productId, Model model) {
		return productService.selectProductId(productId);
	}
	
	//상품 수정하기
	@PostMapping("/admin/update")
	public String updateProduct(Product product, Model model, HttpSession session) {
		productService.updateProduct(product);
		model.addAttribute("product", product);
		session.setAttribute("productId", product.getProductId());
		session.setAttribute("productCode", product.getProductCode());
		session.setAttribute("productNm", product.getProductNm());
		session.setAttribute("productPrice", product.getProductPrice());
		session.setAttribute("productStock", product.getProductStock());
		System.out.println(product.getProductId());
		System.out.println(product.getProductCode());
		System.out.println(product.getProductNm());
		System.out.println(product.getProductPrice());
		System.out.println(product.getProductStock());
		model.addAttribute("message","상품이 수정되었습니다.");
		model.addAttribute("searchUrl","/product/admin");
		return "admin/product/message";
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
