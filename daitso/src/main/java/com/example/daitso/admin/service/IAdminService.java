package com.example.daitso.admin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.category.model.CategoryCheck;
import com.example.daitso.config.CommonCode;
import com.example.daitso.coupon.model.CouponCheck;
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
	Product selectProductById(int productId);
	
	// 상품 수정하기
	void updateProduct(Product product);
	
	// 상품 삭제하기
	void deleteProduct(int productId);
	
	// 상품 등록하기 ★
//	void registerProducts(ProductCheck product, List<MultipartFile> files);
	
	//테스트//
	void registerProducts(ProductCheck product);

	// 상품명을 검색해서 해당 상품 정보 갖고오기
	List<ProductCheck> searchProductsByName(String searchText);

	
	// 주문 내역 조회하기(배송상태별)
	List<PurchaseList> selectPurchaseList(int commonCodeId, int offset, int pageSize);
	
	// 주문 내역 개수 조회하기(전체, 배송상태별)
	int selectCountPurchaseList(int commonCodeId);

	// 배송 상태 변경하기
	void changePurchaseStatus(int purchaseId, int commonCodeId);	
		
	// 주문 내역 검색하기(회원명, 주문번호 선택해서)
	List<PurchaseList> searchPurchaseInfo(String searchText, String searchOption, int offset, int pageSize);
	
	// 검색 결과 개수 조회하기
	int selectCountPurchaseInfo(String searchText, String searchOption);
	
	// 주문번호로 주문 상세 내역 조회하기
	List<PurchaseList> getPurchaseDetails(String purchaseNum);
	
	
	// 전체 카테고리 조회하기
	List<CategoryCheck> selectAllCategories(int offset, int pageSize);
	
	// 전체 카테고리 개수 조회하기
	int selectCountCategories();

	// 카테고리 삭제하기
	void deleteCategory(int categoryId);
	
	// 카테고리ID로 카테고리 정보 조회하기
	CategoryCheck selectCategoryByCategoryId(int categoryId);
	
	// 카테고리 정보 수정하기
	void updateCategoryInfo(CategoryCheck categoryCheck);

//카테고리 등록하기 ★
//	void registerCategories(CategoryCheck categoryCheck, List<MultipartFile> files);
	//테스트//
	void registerCategories(CategoryCheck categoryCheck);
	
	// ★ 중복 상품
	boolean isDuplicateProduct(ProductCheck product);
	
	
	// 최상위 공통코드 조회하기
	List<CommonCode> selectAllCommonCodesPr(@Param("offset") int offset, @Param("pageSize") int pageSize);
		
	// 최상위 공통코드 개수 조회하기
	int selectCountCommonCodesPr();
	
    // 최상위 공통코드 삭제하기
    void deleteCommonCodePr(int commonCodeId);
    
	// 공통코드 조회하기(최상위 공통코드별)
    List<CommonCode> selectCommonCodesByPr(int commonCodeId);
    
    // commonCodeId로 공통코드 정보 조회하기
    CommonCode selectCommonCode(int commonCodeId);
    
    // 공통코드 수정하기
 	void updateCommonCode(CommonCode commonCode);
    
 	// 공통코드 삭제하기
 	void deleteCommonCode(int commonCodeId);
 	
 	void registerCommonCodes(CommonCode commonCode);
 	
	// 전체 쿠폰 조회하기
	List<CouponCheck> selectAllCoupons(@Param("offset") int offset, @Param("pageSize") int pageSize);
	
	// 전체 쿠폰 개수 조회하기
	int selectCountCoupons();
	
	// 쿠폰 삭제하기
	void deleteCoupon(int couponId);

	// 쿠폰 등록하기
	void registerCoupons(CouponCheck couponCheck);
	
	// 쿠폰 일련번호 중복 확인하기
	boolean isCouponSnUnique(String couponSn);
	
}
