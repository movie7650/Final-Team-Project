package com.example.daitso.admin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.category.model.CategoryCheck;
import com.example.daitso.config.CommonCode;
import com.example.daitso.coupon.model.CouponCheck;
import com.example.daitso.customer.model.CustomerChart;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductChart;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.purchase.model.PurchaseChart;
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
	Product selectProductByProductId(int productId);
	
	// 상품 수정하기
	void updateProduct(Product product);
	
	// 상품 삭제하기
	void deleteProduct(int productId);
	
	// 상품 등록하기 ★
	void registerProduct(ProductCheck product, List<MultipartFile> files) throws Exception;
	
	void registerProductOriginal(ProductCheck product);
	
	//테스트//
//	void registerProduct(ProductCheck product);
	
	// 상품 등록시 상품의 중복 여부를 확인하기
    boolean isDuplicateProduct(ProductCheck product);
    
    // 상품 수정시 상품의 중복 여부를 확인하기 -> 변경함
//    boolean isDuplicateProduct(Product product);

	// 상품명을 검색해서 해당 상품 정보 갖고오기
	List<ProductCheck> searchProductsByName(String searchText);
	
	// 상품 이미지 정보 삭제하기
	void deleteProductImages(int productId, boolean deleteFirstImage, boolean deleteSecondImage, boolean deleteThirdImage);
	
	// 상품 이미지 수정하기
//	void updateProductImages(int productId, List<String> imageUrls);
//	void updateProductImages(int productId, String imageUrl);
	void updateProductImages(int productId, int selector, String imageUrl);
	
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
	void registerCategories(CategoryCheck categoryCheck, MultipartFile file);
	//테스트//
//	void registerCategories(CategoryCheck categoryCheck);
	
	void updateCategoryImage(int categoryId, String imageUrl);
	
	void deleteCategoryImage(int categoryId, boolean deleteCategoryImage);	
	
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
	
	//
	List<CouponCheck> selectCouponsByDv(@Param("commonCodeId") int commonCodeId, @Param("offset") int offset, @Param("pageSize") int pageSize);
	
	//
	int selectCountCouponsByDv(@Param("commonCodeId") int commonCodeId);
	
	// 쿠폰 삭제하기
	void deleteCoupon(int couponId);

	// 쿠폰 등록하기
	void registerCoupons(CouponCheck couponCheck);
	
	// 쿠폰 일련번호 중복 확인하기
	boolean isCouponSnUnique(String couponSn);
	
	// 일별(현재 날짜를 기준으로 7일치), 주별(현재 날짜를 기준으로 4주치), 월별(현재 날짜를 기준으로 5개월치) 매출액과 주문량 조회하기
	List<PurchaseChart> selectSalesStatus(String dateType);

	// 당일, 금주, 당월 가장 많이 팔린 상품 상위 5개 조회하기
	List<PurchaseChart> selectTopSelling(String dateType);
	
	// 상품 부족한 재고(10개 미만), 충분한 재고 조회하기
	List<ProductChart> selectProductStocks();
	
	// 현재 시간을 기준으로 5개월 동안의 월별 회원가입 수 조회하기
	List<CustomerChart> getCustomerCounts();
	
	int selectCountPurchaseDv(int purchaseDv);
	
}
