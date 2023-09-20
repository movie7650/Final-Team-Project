package com.example.daitso.admin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.model.CategoryCheck;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.purchase.model.PurchaseList;

public interface IAdminService {
	
	// 상품 조회하기(카테고리별)
    List<ProductCheck> selectProductsByCategory(int firstCategoryId, int secondCategoryId, int thirdCategoryId, int offset, int pageSize);
    
    // 상품 개수 조회하기(카테고리별)
    int selectCountProducts(int firstCategoryId, int secondCategoryId, int thirdCategoryId);
   
	// 상품 조회하기(그룹ID별)
	List<ProductCheck> selectProductsByGroupId(int productGroupId);
	
	// 상품 삭제하기(그룹)
	void deleteProductByGroupId(int productGroupId);
    
	// 상품ID로 상품 정보 갖고오기
	Product selectProductId(int productId);
	
	// 상품 수정하기
	void updateProduct(Product product);
	
	// 상품 삭제하기
	void deleteProduct(int productId);
	
	// 상품 등록하기
	void registerProducts(ProductCheck product, List<MultipartFile> files);
	
	// 상품 등록하기
//	void registerProducts(ProductCheck product);

	// 상품명을 검색해서 해당 상품 정보 갖고오기
	List<ProductCheck> searchProductsByName(String searchText);

	// 주문 내역 조회하기(배송상태별)
	List<PurchaseList> selectPurchaseList(int commonCodeId, int offset, int pageSize);
	
	// 주문 내역 개수 조회하기(배송상태별)
	int selectCountPurchaseList(int commonCodeId);

	// 배송 상태 변경하기
	void changePurchaseStatus(int purchaseId, int commonCodeId);	
		
	// 주문 내역 검색하기(회원명, 주문번호 선택해서)
	List<PurchaseList> searchPurchaseInfo(String searchText, String searchOption, int offset, int pageSize);
	
	// 검색 결과 개수 조회하기
	int selectCountPurchaseInfo(String searchText, String searchOption);
	
	// 주문 상세 내역 조회하기
	List<PurchaseList> getPurchaseDetails(String purchaseNum);
	
	// 전체 카테고리 조회하기
	List<CategoryCheck> selectAllCategories(int offset, int pageSize);
	
	// 전체 카테고리 개수 조회하기
	int selectCountCategories();

	// 카테고리 삭제하기
	void deleteCategory(int categoryId);
	
	CategoryCheck selectCategoryByCategoryId(int categoryId);
	
	void updateCategoryInfo(CategoryCheck categoryCheck);

}
