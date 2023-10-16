package com.example.daitso.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.admin.exceptions.DuplicateProductException;
import com.example.daitso.admin.service.IAdminService;
import com.example.daitso.admin.service.S3Service;
import com.example.daitso.category.model.Category;
import com.example.daitso.category.model.CategoryCheck;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.config.CommonCode;
import com.example.daitso.coupon.model.CouponCheck;
import com.example.daitso.customer.model.CustomerChart;
import com.example.daitso.inquiry.model.InquiryInfo;
import com.example.daitso.inquiry.model.InquiryInfoWithAnswer;
import com.example.daitso.inquiry.model.InquirySelect;
import com.example.daitso.inquiry.service.IInquiryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductChart;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.purchase.model.PageResult;
import com.example.daitso.purchase.model.PurchaseChart;
import com.example.daitso.purchase.model.PurchaseList;
import com.example.daitso.purchase.service.IPurchaseService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	IAdminService adminService;
	
	@Autowired
	ICategoryService categoryService;

	@Autowired
	IPurchaseService purchaseService;
	
	@Autowired
	IInquiryService inquiryService;
	
	@Autowired
	S3Service s3Service;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/purchase-count")
	@ResponseBody
	public Map<String, Integer> getPurchaseCounts() {
	    Map<String, Integer> counts = new HashMap<>();
	    counts.put("depositCount", adminService.selectCountPurchaseDv(401));
	    counts.put("shippingCount", adminService.selectCountPurchaseDv(402));
	    counts.put("shippedCount", adminService.selectCountPurchaseDv(403));
	    return counts;
	}
	
	// 상품 조회하기(카테고리별)
	@GetMapping("/product")
	public String selectProductsByCategory( @RequestParam(name = "firstCategoryId", required = false) Integer firstCategoryId,
				  @RequestParam(name = "secondCategoryId", required = false) Integer secondCategoryId,
				  @RequestParam(name = "thirdCategoryId", required = false) Integer thirdCategoryId,
				  @RequestParam(name = "page", defaultValue = "1") int page,
				  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
	    
		// 해당 페이지에 보여질 상품들 번호 가져오는 것 ex)3 페이지면 offset=20으로 매퍼에서 21(offset+1)번부터 30(offset+pageSize)번까지 보여준다.
	    int offset = (page - 1) * pageSize;
	    
	    // 초기값 설정
	    if (firstCategoryId == null) {
	        firstCategoryId = 0; 
	    }
	    if (secondCategoryId == null) {
	        secondCategoryId = 0; 
	    }
	    if (thirdCategoryId == null) {
	    	thirdCategoryId = 0;
	    }
	    
	    // 카테고리별 조회 상품
	    List<ProductCheck> products = adminService.selectProductsByCategory(firstCategoryId, secondCategoryId, thirdCategoryId, offset, pageSize);
	    model.addAttribute("products", products);
	   	    
	    // 첫 번째 카테고리 불러오기
	    List<Category> firstCategories = categoryService.getAllFirstCategoryIdAndName();
	    model.addAttribute("firstCategories", firstCategories);
	    
	    // 선택한 카테고리ID 정보 전달
	    model.addAttribute("selectedFirstCategoryId", firstCategoryId);
	    model.addAttribute("selectedSecondCategoryId", secondCategoryId);
	    model.addAttribute("selectedThirdCategoryId", thirdCategoryId);

	    // 페이징 정보 전달
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pageSize", pageSize);

	    // 총 상품 개수
	    int totalCount = adminService.selectCountProducts(firstCategoryId, secondCategoryId, thirdCategoryId);
	    
	    // 총 페이지 수
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("totalPages", totalPages);

	    return "admin/product/admin-product";
	}
	
	// 상품 조회하기(JSON - 카테고리별,페이징)
	@GetMapping("/products")
	@ResponseBody
	public PageResult<ProductCheck> selectProductsByCategory(
	        @RequestParam(name = "firstCategoryId", required = false) Integer firstCategoryId,
	        @RequestParam(name = "secondCategoryId", required = false) Integer secondCategoryId,
	        @RequestParam(name = "thirdCategoryId", required = false) Integer thirdCategoryId,
	        @RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

	    int offset = (page - 1) * pageSize;

	    if (firstCategoryId == null) {
	        firstCategoryId = 0; 
	    }
	    if (secondCategoryId == null) {
	        secondCategoryId = 0; 
	    }
	    if (thirdCategoryId == null) {
	        thirdCategoryId = 0;
	    }

	    // 카테고리별 조회 상품
	    List<ProductCheck> products = adminService.selectProductsByCategory(firstCategoryId, secondCategoryId, thirdCategoryId, offset, pageSize);
	   
	    // 총 상품 개수
	    int totalCount = adminService.selectCountProducts(firstCategoryId, secondCategoryId, thirdCategoryId);
	    
	    PageResult<ProductCheck> result = new PageResult<>();
        result.setData(products); // 카테고리별 조회 상품
        result.setCurrentPage(page); // 페이지
        result.setTotalPages((int) Math.ceil((double) totalCount / pageSize)); // 총 페이지 수 (총 상품 개수/ 페이지 크기)

        return result;
	}

	// 두 번째 카테고리 불러오기 ('syn' 및 'unsyn' 값을 인수로 받아 다른 동작 수행)
	@GetMapping("/product/second/{categoryId}")
	@ResponseBody
	public List<Category> getSecondCategories(@PathVariable int categoryId, Model model) {
		List<Category> secondCategories = categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
		model.addAttribute("secondCategories",secondCategories);
		return secondCategories;
	}
		
	// 세 번째 카테고리 불러오기, ('syn' 및 'unsyn' 값을 인수로 받아 다른 동작 수행)
	@GetMapping("/product/third/{categoryId}")
	@ResponseBody
	public List<Category> getThirdCategories(@PathVariable int categoryId, Model model) {
		List<Category> thirdCategories = categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
		model.addAttribute("thirdCategories",thirdCategories);
		return thirdCategories;
	}
	
	// 상품 조회하기(그룹ID별)
	@GetMapping("/product/check/{productGroupId}")
	@ResponseBody
	public List<ProductCheck> product (@PathVariable int productGroupId, Model model) {
		return adminService.selectProductsByGroupId(productGroupId);
	}
	
	// 상품 삭제하기(그룹)
	@PostMapping("/product/delete-group")
	public String deleteProductByGroupId(@RequestParam int productGroupId, Model model) {
		adminService.deleteProductByGroupId(productGroupId);
		model.addAttribute("message","상품이 삭제되었습니다.");
		model.addAttribute("searchUrl","/admin/product");
		return "admin/message";
	}	
	
	// 상품ID로 상품 정보 갖고오기
	@GetMapping("/product/update/{productId}")
	@ResponseBody
	public Product selectProductByProductId(@PathVariable int productId, Model model) {
		return adminService.selectProductByProductId(productId);
	}

	// 상품 수정하기
	@PostMapping("/product/update")
	public String updateProduct(Product product, Model model, HttpSession session) {
		adminService.updateProduct(product);
		model.addAttribute("product", product);
	   	session.setAttribute("productCode", product.getProductCode());
		session.setAttribute("productId", product.getProductId());
		session.setAttribute("productNm", product.getProductNm());
		session.setAttribute("productOptionFirst", product.getProductOptionFirst());
		session.setAttribute("productOptionSecond", product.getProductOptionSecond());
		session.setAttribute("productOptionThird", product.getProductOptionThird());
		session.setAttribute("productPrice", product.getProductPrice());
		session.setAttribute("productStock", product.getProductStock());
		session.setAttribute("productMaxGet", product.getProductMaxGet());
		String successMessage = "상품이 수정되었습니다.";
		model.addAttribute("message", successMessage);
		return "redirect:/admin/product";
	}
	
	// 상품 삭제하기
	@PostMapping("/product/delete")
	public String deleteSelectedProducts(@RequestBody List<Integer> selectedProductIds, Model model) {
	    for (Integer productId : selectedProductIds) {
	        adminService.deleteProduct(productId);
	    }
	    return "admin/product";
	}
	
	// s3에서 이미지 삭제하기
	@PostMapping("/deleteImage")
	@ResponseBody
	public Map<String, String> deleteImage(@RequestBody Map<String, String> requestBody) {
	    Map<String, String> response = new HashMap<>();
	    String imageUrl = requestBody.get("imageUrl");
	    
	    s3Service.deleteImage(imageUrl);
	    
	    return response;
	}
	
	// 상품 이미지 정보 삭제하기
	@PostMapping("/deleteProductImages")
    public ResponseEntity<?> deleteProductImages(@RequestParam int productId,
                                                 @RequestParam(required = false) boolean deleteFirstImage,
                                                 @RequestParam(required = false) boolean deleteSecondImage,
                                                 @RequestParam(required = false) boolean deleteThirdImage) {
        try {
            // 상품 이미지 정보 삭제
            adminService.deleteProductImages(productId, deleteFirstImage, deleteSecondImage, deleteThirdImage);
            // 성공적인 응답 반환
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 오류 발생 시 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
		
	// 상품 이미지 수정하기
	@PostMapping("/updateProductImages")
	public ResponseEntity<?> updateProductImages(@RequestParam int productId,
	                                              @RequestParam(required = false) MultipartFile uploadFirstImage,
	                                              @RequestParam(required = false) MultipartFile uploadSecondImage,
	                                              @RequestParam(required = false) MultipartFile uploadThirdImage ) {
		 int selector = 0;
		 try {
	        String imageUrl = null;
	        
	        if (uploadFirstImage != null) {
	            imageUrl = s3Service.uploadSingle(uploadFirstImage); // 첫 번째 이미지 업로드
	        	System.out.println("uploadFirstImage imageUrl: " + imageUrl);
	        	selector = 1;
	        }
	        if (uploadSecondImage != null) {
	            imageUrl = s3Service.uploadSingle(uploadSecondImage); // 두 번째 이미지 업로드
	            System.out.println("uploadSecondImage imageUrl: " + imageUrl);
	            selector = 2;
	        }
	        if (uploadThirdImage != null) {
	            imageUrl = s3Service.uploadSingle(uploadThirdImage); // 세 번째 이미지 업로드
	            System.out.println("uploadThirdImage imageUrl: " + imageUrl);
	            selector = 3;
	        }

	        adminService.updateProductImages(productId, selector ,imageUrl);   
	        
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// 상품 등록하기 ★
	@PostMapping("/product")
	public String registerProduct(ProductCheck product, Model model, @RequestPart List<MultipartFile> files) {
		// 입력 필드가 비어 있으면 '-'으로 대체
	    if (product.getProductOptionFirst() == null || product.getProductOptionFirst().isEmpty()) {
	        product.setProductOptionFirst("-");
	    }
	    if (product.getProductOptionSecond() == null || product.getProductOptionSecond().isEmpty()) {
	        product.setProductOptionSecond("-");
	    }
	    if (product.getProductOptionThird() == null || product.getProductOptionThird().isEmpty()) {
	        product.setProductOptionThird("-");
	    }
	    
	    try {
	    	adminService.registerProduct(product, files);
	    	String successMessage = "상품이 등록되었습니다.";
	    	model.addAttribute("message", successMessage);
	    } catch (DuplicateProductException e) {
	    	String errorMessage = "상품이 중복되었습니다! 다시 등록해주세요.";
	        model.addAttribute("message", errorMessage);
	    } catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "redirect:/admin/product";
	}
	
	@PostMapping("/product/original")
	public String registerProductOriginal(ProductCheck product, Model model) {
		// 입력 필드가 비어 있으면 '-'으로 대체
	    if (product.getProductOptionFirst() == null || product.getProductOptionFirst().isEmpty()) {
	        product.setProductOptionFirst("-");
	    }
	    if (product.getProductOptionSecond() == null || product.getProductOptionSecond().isEmpty()) {
	        product.setProductOptionSecond("-");
	    }
	    if (product.getProductOptionThird() == null || product.getProductOptionThird().isEmpty()) {
	        product.setProductOptionThird("-");
	    }
	    
	    try {
	    	adminService.registerProductOriginal(product);
//	        model.addAttribute("message", "상품이 등록되었습니다.");
	    	String successMessage = "상품이 등록되었습니다.";
	    	model.addAttribute("message", successMessage);
	    } catch (DuplicateProductException e) {
//	        model.addAttribute("message", "상품이 중복되었습니다! 다시 등록해주세요.");
	    	String errorMessage = "상품이 중복되었습니다! 다시 등록해주세요.";
	        model.addAttribute("message", errorMessage);
	    }	    
//	    model.addAttribute("searchUrl", "/admin/product");
//	    return "admin/message";
	    return "redirect:/admin/product";
	}
	
	//테스트//
//	@PostMapping("/product")
//	public String registerProduct(ProductCheck product, Model model) {
//		// 입력 필드가 비어 있으면 '-'으로 대체
//	    if (product.getProductOptionFirst() == null || product.getProductOptionFirst().isEmpty()) {
//	        product.setProductOptionFirst("-");
//	    }
//	    if (product.getProductOptionSecond() == null || product.getProductOptionSecond().isEmpty()) {
//	        product.setProductOptionSecond("-");
//	    }
//	    if (product.getProductOptionThird() == null || product.getProductOptionThird().isEmpty()) {
//	        product.setProductOptionThird("-");
//	    }
//		adminService.registerProduct(product);
//		model.addAttribute("message","상품이 등록되었습니다.");
//		model.addAttribute("searchUrl","/admin/product");
//		return "admin/message";
//	}	
	
//	@PostMapping("/product")
//	public String registerProduct(ProductCheck product, Model model) {
//	    // 입력 필드가 비어 있으면 '-'으로 대체
//	    if (product.getProductOptionFirst() == null || product.getProductOptionFirst().isEmpty()) {
//	        product.setProductOptionFirst("-");
//	    }
//	    if (product.getProductOptionSecond() == null || product.getProductOptionSecond().isEmpty()) {
//	        product.setProductOptionSecond("-");
//	    }
//	    if (product.getProductOptionThird() == null || product.getProductOptionThird().isEmpty()) {
//	        product.setProductOptionThird("-");
//	    }
//	    
//	    try {
//	        adminService.registerProduct(product);
//	        model.addAttribute("message", "상품이 등록되었습니다.");
//	    } catch (DuplicateProductException e) {
//	        model.addAttribute("message", "상품이 중복되었습니다! 다시 등록해주세요.");
//	    }
//	    model.addAttribute("searchUrl", "/admin/product");
//	    return "admin/message";
	    
//	    // 중복 상품 검사
//	    boolean isDuplicate = adminService.isDuplicateProduct(product);
//	    if (isDuplicate) {
//	        model.addAttribute("message", "상품이 중복되었습니다! 다시 등록해주세요.");
//	    } else {
//	        // 중복 상품이 없을 경우 상품 등록
//	        try {
//	            adminService.registerProduct(product);
//	            model.addAttribute("message", "상품이 등록되었습니다.");
//	        } catch (DuplicateProductException e) {
//	            model.addAttribute("message", "상품 등록 중 오류가 발생했습니다.");
//	        }
//	    }
//	    model.addAttribute("searchUrl", "/admin/product");
//	    return "admin/message";
//	}

	// 중복 예외처리
	@ExceptionHandler(DuplicateProductException.class)
    public String handleDuplicateProductException(DuplicateProductException e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("searchUrl", "/admin/product");
        return "admin/message";
    }
	
	// 상품명으로 상품 검색하기
    @GetMapping("/product/search")
    @ResponseBody
    public List<ProductCheck> searchProducts(@RequestParam("searchText") String searchText) {
        List<ProductCheck> productList = adminService.searchProductsByName(searchText);
        return productList;
    }

    // 주문 내역 조회하기(전체 조회 페이지)
    @GetMapping("/purchase")
	public String selectPurchaseList(@RequestParam(name = "commonCodeId", required = false) Integer commonCodeId,
			  @RequestParam(name = "page", defaultValue = "1") int page,
			  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
		
	    int offset = (page - 1) * pageSize;	   
	    
	    // 초기값 설정
	    if (commonCodeId == null) {
	        commonCodeId = 401; // 입금/결제
	    }
	    
		List<PurchaseList> purchaselist = adminService.selectPurchaseList(commonCodeId, offset, pageSize);
	    model.addAttribute("purchaselist", purchaselist);

	    // 페이징 정보 전달
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pageSize", pageSize);

	    // 총 상품 개수
	    int totalCount = adminService.selectCountPurchaseList(commonCodeId);
	    
	    // 총 페이지 수
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("totalPages", totalPages);

	    return "admin/purchase/admin-purchase";
	}
    
	// 주문 내역 조회하기(배송상태별, 페이지 정보)
    @GetMapping("/purchases")
    @ResponseBody
    public PageResult<PurchaseList> selectPurchaseLists(@RequestParam(name = "commonCodeId", required = false) Integer commonCodeId,
              @RequestParam(name = "page", defaultValue = "1") int page,
              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
        
        int offset = (page - 1) * pageSize;       
        
        if (commonCodeId == null) {
            commonCodeId = 0; 
        }

        List<PurchaseList> purchaselist = adminService.selectPurchaseList(commonCodeId, offset, pageSize);
        
        int totalCount = adminService.selectCountPurchaseList(commonCodeId);

        //페이징된 결과 데이터 넣을 PageResult(페이징된 데이터 목록, 현재 페이지 번호, 총 페이지수 담기)
        PageResult<PurchaseList> result = new PageResult<>();
        result.setData(purchaselist);
        result.setCurrentPage(page);
        result.setTotalPages((int) Math.ceil((double) totalCount / pageSize));

        return result;
    }

    // 배송 상태 변경하기
    @PostMapping("/purchase/change-status")
    public String changePurchaseStatus(int purchaseId, int commonCodeId, Model model) {
        adminService.changePurchaseStatus(purchaseId, commonCodeId);
        model.addAttribute("message","배송 상태가 변경되었습니다.");
		model.addAttribute("searchUrl","/admin/purchase");
		return "admin/message";
    }	 
    
    // 주문 내역 검색하기 (회원명, 주문번호 선택해서)
    @GetMapping("/search-purchase")
    @ResponseBody
    public PageResult<PurchaseList> searchPurchaseInfo(@RequestParam("searchText") String searchText,
                                                      @RequestParam("searchOption") String searchOption,
                                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        int offset = (page - 1) * pageSize;

        List<PurchaseList> purchaseInfo = adminService.searchPurchaseInfo(searchText, searchOption, offset, pageSize);
        
        // 총 개수 조회하기
        int totalCount = adminService.selectCountPurchaseInfo(searchText, searchOption);

        PageResult<PurchaseList> result = new PageResult<>();
        result.setData(purchaseInfo);
        result.setCurrentPage(page);
        result.setTotalPages((int) Math.ceil((double) totalCount / pageSize));

        return result;
    }
  
    // 주문번호로 주문 상세 내역 조회하기
    @GetMapping("/purchase-details/{purchaseNum}")
    @ResponseBody
    public List<PurchaseList> getPurchaseDetails(@PathVariable String purchaseNum) {
    	List<PurchaseList> purchaselist = adminService.getPurchaseDetails(purchaseNum);
        return purchaselist;
    }  

    // 전체 카테고리 조회하기
  	@GetMapping("/category")
  	public String selectAllCagegory(@RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
       	
      int offset = (page - 1) * pageSize;       
      
      List<CategoryCheck> categorylist = adminService.selectAllCategories(offset, pageSize);
      
      	// 첫 번째 카테고리 불러오기
	    List<Category> firstCategories = categoryService.getAllFirstCategoryIdAndName();
	    model.addAttribute("firstCategories", firstCategories);
	    
       // 페이징 정보 전달 
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pageSize", pageSize);

	    // 총 상품 개수
	    int totalCount = adminService.selectCountCategories(); 
	    
	    // 총 페이지 수
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("totalPages", totalPages);
        model.addAttribute("categorylist",categorylist);
        
        return "admin/category/admin-category";
  	}
	
  	// 카테고리 삭제하기
    @PostMapping("/category/delete")
	public String deleteCategory(@RequestParam int categoryId, Model model) {
		adminService.deleteCategory(categoryId);
		model.addAttribute("message","카테고리가 삭제되었습니다.");
		model.addAttribute("searchUrl","/admin/category");
		return "admin/message";
	}	
  	
    // 카테고리ID로 카테고리 정보 조회하기
	@GetMapping("/category/update/{categoryId}")
	@ResponseBody
	public CategoryCheck selectCategoryByCategoryId(@PathVariable int categoryId, Model model) {
		return adminService.selectCategoryByCategoryId(categoryId);
	}
	
	// 카테고리 정보 수정하기
 	@PostMapping("/update/category")
 	public String updateCategoryInfo(CategoryCheck categoryCheck, Model model, HttpSession session) { 	    
 		adminService.updateCategoryInfo(categoryCheck);
 		model.addAttribute("categoryCheck", categoryCheck);
 	   	session.setAttribute("categoryId",categoryCheck.getCategoryId());
 		session.setAttribute("categoryNm",categoryCheck.getCategoryNm());
 		session.setAttribute("categoryContent",categoryCheck.getCategoryContent());
 		session.setAttribute("categoryImage",categoryCheck.getCategoryImage());
 		model.addAttribute("message","카테고리가 수정되었습니다.");
 		model.addAttribute("searchUrl","/admin/category");
 		return "admin/message";
 	}
 	
 	// 카테고리 등록하기 ★
	@PostMapping("/category")
	public String registerCategories(CategoryCheck categoryCheck, Model model, @RequestPart MultipartFile file) {
		adminService.registerCategories(categoryCheck, file);
		model.addAttribute("message","카테고리가 등록되었습니다.");
		model.addAttribute("searchUrl","/admin/category");
	return "admin/message";
	
	}
	
	// 카테고리 이미지 수정하기
	@PostMapping("/updateCategoryImage")
	public ResponseEntity<?> updateCategoryImage( @RequestParam int categoryId,
	                                              @RequestParam(required = false) MultipartFile uploadCategoryImage) {
		 try {
	        String imageUrl = null;
	        if (uploadCategoryImage != null) {
	            imageUrl = s3Service.uploadSingle(uploadCategoryImage); // 카테고리 이미지 업로드
	        	System.out.println("uploadCategoryImage imageUrl: " + imageUrl);
	        }
	        adminService.updateCategoryImage(categoryId, imageUrl);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	// 카테고리 이미지 삭제하기
	@PostMapping("/deleteCategoryImage")
    public ResponseEntity<?> deleteCategoryImage(@RequestParam int categoryId,
                                                 @RequestParam(required = false) boolean deleteCategoryImage) {
        try {
            // 상품 이미지 정보 삭제
            adminService.deleteCategoryImage(categoryId, deleteCategoryImage);
            // 성공적인 응답 반환
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 오류 발생 시 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
			
	// 전체 공통 코드 조회하기
  	@GetMapping("/common-code")
  	public String selectAllCommonCodesPr(@RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
        
      int offset = (page - 1) * pageSize;       
     
      	List<CommonCode> commonCodes = adminService.selectAllCommonCodesPr(offset, pageSize);
      
       // 페이징 정보 전달
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pageSize", pageSize);

	    // 총 상품 개수
	    int totalCount = adminService.selectCountCommonCodesPr(); 
	    
	    // 총 페이지 수
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("totalPages", totalPages);
        model.addAttribute("commonCodes",commonCodes);
        
        return "admin/commoncode/admin-commoncode";
  	}
  	
 	// 공통 코드 등록하기
 	@PostMapping("/common-code")
	public String registerCommonCodes(CommonCode commonCode, Model model) {
		adminService.registerCommonCodes(commonCode);
		model.addAttribute("message","공통코드가 등록되었습니다.");
		model.addAttribute("searchUrl","/admin/common-code");
	return "admin/message";
	}
 	
  	// 공통코드 조회하기(최상위 공통코드별)
 	@GetMapping("/check/{commonCodeId}")
 	@ResponseBody
 	public List<CommonCode> commonCodes (@PathVariable int commonCodeId, Model model) {
 		return adminService.selectCommonCodesByPr(commonCodeId);
 	}
 	
 	// 최상위 공통코드 삭제하기
 	@PostMapping("/delete-common")
 	public String deleteCommonCodePr(@RequestParam int commonCodeId, Model model) {
 		adminService.deleteCommonCodePr(commonCodeId);
 		model.addAttribute("message","공통코드가 삭제되었습니다.");
 		model.addAttribute("searchUrl","/admin/common-code");
 		return "admin/message";
 	}	
  	
 	// commonCodeId로 공통코드 정보 조회하기
 	@GetMapping("/update-common/{commonCodeId}")
 	@ResponseBody
 	public CommonCode selectCommonCode(@PathVariable int commonCodeId, Model model) {
 		return adminService.selectCommonCode(commonCodeId);
 	}

 	// 공통코드 수정하기
 	@PostMapping("/update-common")
 	public String updateCommonCode(CommonCode commonCode, Model model, HttpSession session) {
 		adminService.updateCommonCode(commonCode);
 		model.addAttribute("commonCode", commonCode);
 	   	session.setAttribute("commonCodeId", commonCode.getCommonCodeId());
 		session.setAttribute("commonCodeNm", commonCode.getCommonCodeNm());
 		session.setAttribute("status", commonCode.getStatus());
 		model.addAttribute("message","공통코드가 수정되었습니다.");
 		model.addAttribute("searchUrl","/admin/common-code");
 		return "admin/message";
 	}
 	
 	// 공통코드 삭제하기
 	@PostMapping("/delete-common-code")
 	public String deleteCommonCode(@RequestBody List<Integer> selectedCommonCodeIds, Model model) {
 	    for (Integer commonCodeId : selectedCommonCodeIds) {
 	        adminService.deleteCommonCode(commonCodeId);
 	    }
 	    return "admin/product";
 	}
 	
 	// 전체 쿠폰 조회하기
   	@GetMapping("/coupon")
   	public String selectAllCoupons(@RequestParam(name = "page", defaultValue = "1") int page,
             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
         
       int offset = (page - 1) * pageSize;       
       

       	List<CouponCheck> couponChecks = adminService.selectAllCoupons(offset, pageSize);
       
       	// 첫 번째 카테고리 불러오기
	    List<Category> firstCategories = categoryService.getAllFirstCategoryIdAndName();
	    model.addAttribute("firstCategories", firstCategories);
	    
        // 페이징 정보 전달
 	    model.addAttribute("currentPage", page);
 	    model.addAttribute("pageSize", pageSize);

 	    // 총 상품 개수
 	    int totalCount = adminService.selectCountCoupons(); 
 	    
 	    // 총 페이지 수
 	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
 	    
 	    model.addAttribute("totalCount", totalCount);
 	    model.addAttribute("totalPages", totalPages);
        model.addAttribute("couponChecks",couponChecks);
         
       return "admin/coupon/admin-coupon";
   	}
 	
 	// 쿠폰 삭제하기
 	@PostMapping("/coupon/delete")
 	public String deleteCoupon(@RequestParam int couponId, Model model) {
 		adminService.deleteCoupon(couponId);
 		model.addAttribute("message","쿠폰이 삭제되었습니다.");
 		model.addAttribute("searchUrl","/admin/coupon");
 		return "admin/message";
 	}	
 	
 	// 쿠폰 등록하기
 	@PostMapping("/coupon")
 	public String registerCoupons(@RequestParam("couponSn") String couponSn, CouponCheck couponCheck, Model model) {
 		 if (adminService.isCouponSnUnique(couponSn)) {
 		     couponCheck.setCouponSn(couponSn);
 		     adminService.registerCoupons(couponCheck);
 		        model.addAttribute("message", "쿠폰이 등록되었습니다.");
 		 } else {
 		     model.addAttribute("message", "쿠폰 등록에 실패하였습니다. 다시 시도해주세요.");
 		 }
 		 model.addAttribute("searchUrl", "/admin/coupon");
 		 return "admin/message";
 	}
 	
// 	// 쿠폰 중복 확인하기
// 	@PostMapping("/checkCouponUniqueness")
// 	@ResponseBody
// 	public ResponseEntity<Boolean> checkCouponUniqueness(@RequestParam("couponSn") String couponSn) {
// 	    boolean isUnique = adminService.isCouponSnUnique(couponSn);
// 	    return ResponseEntity.ok(isUnique);
// 	}
 	
 	
 	@GetMapping("/chart")
    public String showAdminChart(Model model) {
        return "admin/admin-chart";
    }  
 	
 	
 	// 일별(현재 날짜를 기준으로 7일치), 주별(현재 날짜를 기준으로 4주치), 월별(현재 날짜를 기준으로 5개월치) 매출액과 주문량 조회하기
 	@GetMapping("/sales-chart")
    @ResponseBody
    public Map<String, Object> renderSalesChart(@RequestParam("dateType") String dateType) {
        Map<String, Object> response = new HashMap<>();
        List<PurchaseChart> saleslists = null;
        
        if ("day".equals(dateType)) {
        	saleslists = adminService.selectSalesStatus(dateType);
        } else if ("week".equals(dateType)) {
        	saleslists = adminService.selectSalesStatus(dateType);
        } else if ("month".equals(dateType)) {
        	saleslists = adminService.selectSalesStatus(dateType);
        }else {
          response.put("error", "Invalid dateType");
          return response;
      }

        List<String> dates = new ArrayList<>(); //기간
        List<Integer> orderCounts = new ArrayList<>(); //주문건수
        List<Integer> revenueData = new ArrayList<>(); //매출액

        for (PurchaseChart saleslist : saleslists) {
            dates.add(saleslist.getResult());
            orderCounts.add(saleslist.getCount());
            revenueData.add(saleslist.getTotalCost());
        }

        response.put("dates", dates);
        response.put("orderCounts", orderCounts);
        response.put("revenueData", revenueData);

        return response;
    }

    
 	// 당일, 금주, 당월 가장 많이 팔린 상품 상위 5개 조회하기
    @GetMapping("/selling-pie")
    @ResponseBody 
    public Map<String, Object> showBar(@RequestParam("dateType") String dateType) {
        Map<String, Object> response = new HashMap<>();
        List<PurchaseChart> sellinglists = null;
        
        if ("day".equals(dateType)) {
        	sellinglists = adminService.selectTopSelling(dateType);
        } else if ("week".equals(dateType)) {
        	sellinglists = adminService.selectTopSelling(dateType);
        } else if ("month".equals(dateType)) {
        	sellinglists = adminService.selectTopSelling(dateType);
        }else {
          response.put("error", "Invalid dateType");
          return response;
      }

        List<String> dates = new ArrayList<>();
        List<String> productNames = new ArrayList<>();
        List<Integer> salesData = new ArrayList<>();

        for (PurchaseChart sellinglist : sellinglists) {
            dates.add(sellinglist.getResult());
            productNames.add(sellinglist.getProductNm());
            salesData.add(sellinglist.getCount());
        }

        response.put("dates", dates);
        response.put("productNames", productNames);
        response.put("salesData", salesData);
        
        System.out.println(dates);
        System.out.println(productNames);
        System.out.println(salesData);

        return response;
    }
    
    
    // 상품 부족한 재고(10개 미만), 충분한 재고 조회하기
    @GetMapping("/stock-pie")
    @ResponseBody
    public List<ProductChart> getProductChart() {
        List<ProductChart> productStocks = adminService.selectProductStocks();
        return productStocks; 
    }
    
    // 현재 시간을 기준으로 5개월 동안의 월별 회원가입 수 조회하기
    @GetMapping("/signup-bar")
    @ResponseBody
    public List<CustomerChart> getCustomerCounts() {
        List<CustomerChart> customerCounts = adminService.getCustomerCounts();
        return customerCounts; 
    }

    
    
	// 카테고리 수정하기
	@GetMapping("/category/update")
	public String updateCategory(Model model) {
		List<Category> list = categoryService.selectAllCategory();
		model.addAttribute("categoryList", list);
		System.out.println(list);
		return "admin/category/category-update";
	}
	
	// 카테고리 수정하기
	@PostMapping("/category/update")
	public String updateCategory(Model model, int parentCategoryId, int categoryId) {
		categoryService.updateCategory(categoryId, parentCategoryId);
		return updateCategory(model);
	}

	// 문의 관리 화면
	@GetMapping("/inquiry/{inquiryAnsDv}")
	public String getInquiry(@PathVariable char inquiryAnsDv, Model model) {
		return getInquiry(inquiryAnsDv, 1, model);
	}
	
	@GetMapping("/inquiry/{inquiryAnsDv}/{page}")
	public String getInquiry(@PathVariable char inquiryAnsDv, @PathVariable int page, Model model) {
		
		model.addAttribute("inquiryAnsDv", inquiryAnsDv);
			
		List<InquirySelect> inquiryList = inquiryService.selectInquiryListByInquiryAnsDv(inquiryAnsDv, page);
		model.addAttribute("inquiryList", inquiryList);
		
		int inquiryTotalCount = inquiryService.selectTotalInquiryCountByInquiryAnsDv(inquiryAnsDv);
		
		int totalPage = 0;
		
		if(inquiryTotalCount > 0) {
			totalPage = (int)Math.ceil(inquiryTotalCount/10.0); // 총 페이지 개수
		}
		
		int totalPageBlock = (int)Math.ceil(totalPage/10.0);
		int nowPageBlock = (int)Math.ceil(page/10.0);
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage = 0;
		
		if(totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		} else if(totalPage == 0) {
			endPage = 1;
		}
		else {
			endPage= totalPage;
		}
		
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "admin/inquiry/admin-inquiry";
	}

	// 문의 답변 화면
	@GetMapping("/inquiry/complete/{inquiryId}/{inquiryAnsDv}")
	public String getInquiryComplete(@PathVariable int inquiryId, @PathVariable char inquiryAnsDv, Model model) {
		
		model.addAttribute("inquiryId", inquiryId);
		model.addAttribute("inquiryAnsDv", inquiryAnsDv);
		
		if(inquiryAnsDv == 'W') {
			InquiryInfo inquiryInfo = inquiryService.selectInquiryInfoByInquiryId(inquiryId);
			model.addAttribute("inquiryInfo", inquiryInfo);
		} else {
			InquiryInfoWithAnswer inquiryInfo = inquiryService.selectInquiryInfoWithAnswerByInquiryId(inquiryId);
			model.addAttribute("inquiryInfo", inquiryInfo);
			model.addAttribute("ansInquiryInfo", inquiryInfo.getAnsInquiryId());
		}
		
		return "admin/inquiry/admin-inquiry-complete";
	}
	
	// 문의 답변
	@PostMapping("/inquiry/insert/{inquiryId}")
	public @ResponseBody String insertInquiry(@PathVariable int inquiryId, @RequestBody String data) {
		JsonElement element = JsonParser.parseString(data);
		
		int productId = Integer.valueOf(element.getAsJsonObject().get("productId").getAsString());
		String inquiryContent = element.getAsJsonObject().get("inquiryContent").getAsString();
		
		inquiryService.insertInquiryComplete(inquiryId, productId, inquiryContent);
		
		return null;
	}
	
	// 문의 삭제
	@PostMapping("/inquiry/delete")
	public @ResponseBody String deleteInquiry(@RequestBody String data) {
		JsonElement element = JsonParser.parseString(data);
		
		int inquiryId = Integer.valueOf(element.getAsJsonObject().get("inquiryId").getAsString());
		int ansInquiryId = Integer.valueOf(element.getAsJsonObject().get("ansInquiryId").getAsString());
		
		inquiryService.deleteInquiryAdmin(inquiryId, ansInquiryId);
		
		return "ok";
	}
	
	
	// 문의 검색 후 화면
	@GetMapping("/inquiry/{inquiryAnsDv}/{page}/{searchFilter}/{search}")
	public String getSearchInquiry(@PathVariable char inquiryAnsDv, @PathVariable int page, @PathVariable String searchFilter, @PathVariable String search, Model model) {
		
		model.addAttribute("inquiryAnsDv", inquiryAnsDv);
			
		List<InquirySelect> inquiryList = inquiryService.selectInquiryListByInquiryAnsDvAndSearch(inquiryAnsDv, searchFilter, search, page);
		model.addAttribute("inquiryList", inquiryList);
		
		System.out.println("hahahahahah" + inquiryList.size());
		
		int inquiryTotalCount = inquiryList.size();
		
		int totalPage = 0;
		
		if(inquiryTotalCount > 0) {
			totalPage = (int)Math.ceil(inquiryTotalCount/10.0); // 총 페이지 개수
		}
		
		int totalPageBlock = (int)Math.ceil(totalPage/10.0);
		int nowPageBlock = (int)Math.ceil(page/10.0);
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage = 0;
		
		if(totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		} else if(totalPage == 0) {
			endPage = 1;
		}
		else {
			endPage= totalPage;
		}
		
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "admin/inquiry/admin-inquiry";
	}
	
	
}
