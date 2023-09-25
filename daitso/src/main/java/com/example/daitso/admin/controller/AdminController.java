package com.example.daitso.admin.controller;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.admin.service.IAdminService;
import com.example.daitso.category.model.Category;
import com.example.daitso.category.model.CategoryCheck;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.config.CommonCode;
import com.example.daitso.inquiry.model.InquiryInfo;
import com.example.daitso.inquiry.model.InquiryInfoWithAnswer;
import com.example.daitso.inquiry.model.InquirySelect;
import com.example.daitso.inquiry.service.IInquiryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.purchase.model.PageResult;
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

	    List<ProductCheck> products = adminService.selectProductsByCategory(firstCategoryId, secondCategoryId, thirdCategoryId, offset, pageSize);
	    model.addAttribute("products", products);
	   	    
	    // 첫 번째 카테고리 불러오기
	    List<Category> firstCategories = categoryService.getAllFirstCategoryIdAndName();
	    model.addAttribute("firstCategories", firstCategories);
	    
	    // 선택한 카테고리 정보 전달
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

	    List<ProductCheck> products = adminService.selectProductsByCategory(firstCategoryId, secondCategoryId, thirdCategoryId, offset, pageSize);

	    int totalCount = adminService.selectCountProducts(firstCategoryId, secondCategoryId, thirdCategoryId);
	    
	    PageResult<ProductCheck> result = new PageResult<>();
        result.setData(products);
        result.setCurrentPage(page);
        result.setTotalPages((int) Math.ceil((double) totalCount / pageSize));

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
	@GetMapping("/search/{productGroupId}")
	@ResponseBody
	public List<ProductCheck> product (@PathVariable int productGroupId, Model model) {
		return adminService.selectProductsByGroupId(productGroupId);
	}
	
	// 상품 삭제하기(그룹)
	@PostMapping("/delete-group")
	public String deleteProductByGroupId(@RequestParam int productGroupId, Model model) {
		adminService.deleteProductByGroupId(productGroupId);
		model.addAttribute("message","상품이 삭제되었습니다.");
		model.addAttribute("searchUrl","/admin/product");
		return "admin/message";
	}	
	
	// 상품ID로 상품 정보 갖고오기
	@GetMapping("/update/{productId}")
	@ResponseBody
	public Product selectProductId(@PathVariable int productId, Model model) {
		return adminService.selectProductId(productId);
	}

	// 상품 수정하기
	@PostMapping("/update")
	public String updateProduct(Product product, Model model, HttpSession session) {
		// 각 필드를 Jsoup.clean으로 처리
//	    String productCode = Jsoup.clean(product.getProductCode(), Whitelist.basic());
//	    String productNm = Jsoup.clean(product.getProductNm(), Whitelist.basic());
//	    String productOptionFirst = Jsoup.clean(product.getProductOptionFirst(), Whitelist.basic());
//	    String productOptionSecond = Jsoup.clean(product.getProductOptionSecond(), Whitelist.basic());
//	    String productOptionThird = Jsoup.clean(product.getProductOptionThird(), Whitelist.basic());
	    
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
		model.addAttribute("message","상품이 수정되었습니다.");
		model.addAttribute("searchUrl","/admin/product");
		return "admin/message";
	}
	
	// 상품 삭제하기
	@PostMapping("/delete")
	public String deleteSelectedProducts(@RequestBody List<Integer> selectedProductIds, Model model) {
	    for (Integer productId : selectedProductIds) {
	        adminService.deleteProduct(productId);
	    }
	    return "admin/product";
	}
	
	// 상품 등록하기 ★
//	@PostMapping("/product")
//	public String registerProducts(ProductCheck product, Model model, @RequestPart List<MultipartFile> files) {
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
//		adminService.registerProducts(product, files);
//		model.addAttribute("message","상품이 등록되었습니다.");
//		model.addAttribute("searchUrl","/admin/product");
//
//	return "admin/message";
//	}
	
	//테스트//
	@PostMapping("/product")
	public String registerProducts(ProductCheck product, Model model) {
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
		adminService.registerProducts(product);
		model.addAttribute("message","상품이 등록되었습니다.");
		model.addAttribute("searchUrl","/admin/product");
		return "admin/message";
	}
	
	
	// 상품명으로 상품 검색하기
    @GetMapping("/search-product")
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
	    	commonCodeId = 0; 
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
    public String changePurchaseStatus(@RequestParam int purchaseId, @RequestParam int commonCodeId, Model model) {
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
	@GetMapping("/update/category/{categoryId}")
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
 		model.addAttribute("message","카테고리가 수정되었습니다.");
 		model.addAttribute("searchUrl","/admin/category");
 		return "admin/message";
 	}
 	
 	
// 카테고리 등록하기
	@PostMapping("/category")
	public String registerCategories(CategoryCheck categoryCheck, Model model, @RequestPart List<MultipartFile> files) {
		adminService.registerCategories(categoryCheck, files);
		model.addAttribute("message","카테고리가 등록되었습니다.");
		model.addAttribute("searchUrl","/admin/category");
	return "admin/message";
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
  	
  	// 
 	@GetMapping("/check/{commonCodeId}")
 	@ResponseBody
 	public List<CommonCode> commonCodes (@PathVariable int commonCodeId, Model model) {
 		return adminService.selectCommonCodesByPr(commonCodeId);
 	}
 	
 	// 
 	@PostMapping("/delete-common")
 	public String deleteCommonCodePr(@RequestParam int commonCodeId, Model model) {
 		adminService.deleteCommonCodePr(commonCodeId);
 		model.addAttribute("message","공통코드가 삭제되었습니다.");
 		model.addAttribute("searchUrl","/admin/common-code");
 		return "admin/message";
 	}	
  	
 	//
 	@GetMapping("/update-common/{commonCodeId}")
 	@ResponseBody
 	public CommonCode selectCommonCode(@PathVariable int commonCodeId, Model model) {
 		return adminService.selectCommonCode(commonCodeId);
 	}

 	//
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
 	
 	
 	@PostMapping("/delete-common-code")
 	public String deleteCommonCode(@RequestBody List<Integer> selectedCommonCodeIds, Model model) {
 	    for (Integer commonCodeId : selectedCommonCodeIds) {
 	        adminService.deleteCommonCode(commonCodeId);
 	    }
 	    return "admin/product";
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

}
