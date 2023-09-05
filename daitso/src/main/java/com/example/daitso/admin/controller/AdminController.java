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
	
//	//상품 조회하기 (페이징)
//	@GetMapping("/product")
//	public String selectCategoryPagedProducts(Model model,
//	        @RequestParam(defaultValue = "1") int page,
//	        @RequestParam(defaultValue = "10") int pageSize,
//	        @RequestParam(required = false) Integer categoryId) {
//	    int totalCount;
//	    if (categoryId == null) {
//	        totalCount = adminService.getTotalProductCount(); // 전체 상품 갯수
//	    } else {
//	        totalCount = adminService.getCategoryTotalProductCount(categoryId); // 선택한 카테고리의 상품 갯수
//	    }
//	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
//
//	    // 페이지 번호를 범위 내로 제한
//	    if (page < 1) {
//	        page = 1;
//	    } else if (page > totalPages) {
//	        page = totalPages;
//	    }
//
//	    int startRow = (page - 1) * pageSize + 1;
//	    int endRow = startRow + pageSize - 1;
//
//	    List<Product> products;
//	    if (categoryId == null) {
//	        products = adminService.selectPagedProducts(startRow, endRow); // 전체 상품 조회
//	        
//	    } else {
//	        products = adminService.selectCategoryPagedProducts(categoryId, startRow, endRow); // 선택한 카테고리의 상품 조회
//	    }
//	    
//	    List<Category> categories = categoryService.getAllFirstCategoryIdAndName();
//
//	    model.addAttribute("products", products);
//	    model.addAttribute("categories", categories);
//
//	    model.addAttribute("currentPage", page);
//	    model.addAttribute("pageSize", pageSize);
//	    model.addAttribute("totalCount", totalCount);
//	    model.addAttribute("totalPages", totalPages);
//
//	    return "admin/product/admin-product";
//	}

	@GetMapping("/product")

	public String selectProducts(
	    @RequestParam(name = "firstCategoryId", required = false) Integer firstCategoryId,
	    @RequestParam(name = "secondCategoryId", required = false) Integer secondCategoryId,
	    Model model) {
	    // 초기값 설정
	    if (firstCategoryId == null) {
	        firstCategoryId = 0; // 상위 카테고리의 초기값
	    }
	    if (secondCategoryId == null) {
	        secondCategoryId = 0; // 하위 카테고리의 초기값
	    }

	    // 매퍼 메서드 호출
	    List<Product> products = adminService.selectProducts(firstCategoryId, secondCategoryId);

	    // 결과를 모델에 추가
	    model.addAttribute("products", products);

	    // 첫 번째 카테고리 불러오기
	    List<Category> firstCategories = categoryService.getAllFirstCategoryIdAndName();
	    model.addAttribute("firstCategories", firstCategories);

	    // 선택한 카테고리 정보 전달
	    model.addAttribute("selectedFirstCategoryId", firstCategoryId);
	    model.addAttribute("selectedSecondCategoryId", secondCategoryId);

	    // 이후 뷰 이름을 반환
	    return "admin/product/admin-product";
	}
	
	//두 번째 카테고리 불러오기
		@GetMapping("/product/{categoryId}")
		@ResponseBody
		public List<Category> getSecondCategories(@PathVariable int categoryId,Model model) {
			List<Category> secondCategories = categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
			model.addAttribute("secondCategories",secondCategories);
			return secondCategories;
		}
	
	//상품 등록하기
	@PostMapping("/product")
	public String registerProducts(Product product, Model model, @RequestPart List<MultipartFile> files) {
		if(files.size() > 3) {
			model.addAttribute("message","상품 이미지 적정 개수를 초과하였습니다!(최대 3개)");
			model.addAttribute("searchUrl","/admin/product");
			return "admin/product/message";
		} else {
			adminService.registerProducts(product, files);
			model.addAttribute("message","상품이 등록되었습니다.");
			model.addAttribute("searchUrl","/admin/product");
			return "admin/product/message";
		}	
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
