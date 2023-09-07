package com.example.daitso.admin.controller;

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

import com.example.daitso.admin.service.IAdminService;
import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductAdmin;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	IAdminService adminService;
	@Autowired
	ICategoryService categoryService;

	// 로그인
	@GetMapping("/login")
	public String login() {
		return "admin/login/admin-login";
	}

	//상품 전체, 카테고리별 조회(페이징)
	@GetMapping("/product")
	public String selectProducts(
	    @RequestParam(name = "firstCategoryId", required = false) Integer firstCategoryId,
	    @RequestParam(name = "secondCategoryId", required = false) Integer secondCategoryId,
	    @RequestParam(name = "page", defaultValue = "1") int page,
	    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
	    Model model) {
	    
	    // 페이지 번호와 페이지 크기를 이용해 페이징 정보 계산
	    int offset = (page - 1) * pageSize;
	    
	    // 초기값 설정
	    if (firstCategoryId == null) {
	        firstCategoryId = 0; // 상위 카테고리의 초기값
	    }
	    if (secondCategoryId == null) {
	        secondCategoryId = 0; // 하위 카테고리의 초기값
	    }

	    List<ProductAdmin> products = adminService.selectProducts(firstCategoryId, secondCategoryId, offset, pageSize);
	    model.addAttribute("products", products);
	   	    
	    // 첫 번째 카테고리 불러오기(필터)
	    List<Category> firstCategories = categoryService.getAllFirstCategoryIdAndName();
	    model.addAttribute("firstCategories", firstCategories);
	    
	    // 선택한 카테고리 정보 전달
	    model.addAttribute("selectedFirstCategoryId", firstCategoryId);
	    model.addAttribute("selectedSecondCategoryId", secondCategoryId);
	    
	    // 페이징 정보 전달
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pageSize", pageSize);

	    // 총 페이지 수 계산
	    int totalCount = adminService.selectCountProducts(firstCategoryId, secondCategoryId);
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("totalPages", totalPages);

	    return "admin/product/admin-product";
	}

	
	//두 번째 카테고리 불러오기(필터)
	@GetMapping("/product/{categoryId}")
	@ResponseBody
	public List<Category> getSecondCategories(@PathVariable int categoryId, Model model) {
		List<Category> secondCategories = categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
		model.addAttribute("secondCategories",secondCategories);
		return secondCategories;
	}
		
	//상품 등록하기
	@PostMapping("/product")
	public String registerProducts(Product product, Model model, @RequestPart List<MultipartFile> files) {
		adminService.registerProducts(product, files);
		model.addAttribute("message","상품이 등록되었습니다.");
		model.addAttribute("searchUrl","/admin/product");
		return "admin/product/message";
	}

	//상품 삭제하기
	@PostMapping("/delete")
	public String deleteProduct(@RequestParam int productId, Model model) {
		adminService.deleteProduct(productId);
		model.addAttribute("message","상품이 삭제되었습니다.");
		model.addAttribute("searchUrl","/admin/product");
		return "admin/product/message";
	}

	//상품 수정을 위해 해당 상품 정보 불러오기
	@GetMapping("/update/{productId}")
	@ResponseBody
	public Product selectProductId(@PathVariable int productId, Model model) {
		return adminService.selectProductId(productId);
	}

	//상품 수정하기
	@PostMapping("/update")
	public String updateProduct(Product product, Model model, HttpSession session) {
		adminService.updateProduct(product);
		model.addAttribute("product", product);
		session.setAttribute("productId", product.getProductId());
		session.setAttribute("productCode", product.getProductCode());
		session.setAttribute("productNm", product.getProductNm());
		session.setAttribute("productPrice", product.getProductPrice());
		session.setAttribute("productStock", product.getProductStock());
		model.addAttribute("message","상품이 수정되었습니다.");
		model.addAttribute("searchUrl","/admin/product");
		return "admin/product/message";
	}

}
