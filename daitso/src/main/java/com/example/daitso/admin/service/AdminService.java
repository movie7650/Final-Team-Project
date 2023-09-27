package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.category.model.CategoryCheck;
import com.example.daitso.category.repository.ICategoryRepository;
import com.example.daitso.config.CommonCode;
import com.example.daitso.config.repository.ICommonCodeRepository;
import com.example.daitso.coupon.model.CouponCheck;
import com.example.daitso.coupon.repository.ICouponRepository;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.product.repository.IProductRepository;
import com.example.daitso.purchase.model.PurchaseList;
import com.example.daitso.purchase.repository.IPurchaseRepository;

@Service
public class AdminService implements IAdminService{
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	ICategoryRepository categoryRepository;
	
	@Autowired
	IPurchaseRepository purchaseRepository;
	
	@Autowired
	ICommonCodeRepository commonCodeRepository;
	
	@Autowired
	ICouponRepository couponRepository;
	
	@Autowired
	S3Service s3Service;
	
	// 상품 등록하기 ★
//	@Transactional
//	public void registerProducts(ProductCheck product, List<MultipartFile> files) {
//		List<String> imagePathList = s3Service.upload(files);
//		product.setProductImageFirst(imagePathList.get(0));
//		product.setProductImageSecond(imagePathList.get(1));
//		product.setProductImageThird(imagePathList.get(2));
//		
//		// 상품 등록 실패시 s3에 등록된 이미지 삭제
//		try {
//			productRepository.registerProducts(product);
//			product.getProductId();
//		} catch(Exception e) {
//			e.printStackTrace();
//			imagePathList.forEach((url) -> {
//				s3Service.deleteImage(url);	
//			});
//		}
//	}
	
	//테스트//
	@Transactional
	public void registerProducts(ProductCheck product) {
		 if (isDuplicateProduct(product)) {
	            // 중복 상품이 이미 존재하므로 등록을 막음
			 throw new RuntimeException("상품이 중복되었습니다.");
	        }

		productRepository.registerProducts(product);
		
	}
	
	// ★
	@Override
	public boolean isDuplicateProduct(ProductCheck product) {
		// 중복 체크 로직 구현
        int count = productRepository.countDuplicateProducts(product);
        return count > 0;
	}
	
	
	// 상품 조회하기(카테고리별)
	@Override
	public List<ProductCheck> selectProductsByCategory(int firstCategoryId, int secondCategoryId, int thirdCategoryId, int offset, int pageSize) {
		return productRepository.selectProductsByCategory(firstCategoryId, secondCategoryId, thirdCategoryId, offset, pageSize);
	}

	// 상품 개수 조회하기(카테고리별)
	@Override
	public int selectCountProducts(int firstCategoryId, int secondCategoryId, int thirdCategoryId) {
		return productRepository.selectCountProducts(firstCategoryId, secondCategoryId, thirdCategoryId);
	}
	
	// 상품 조회하기(그룹ID별)
	@Override
	public List<ProductCheck> selectProductsByGroupId(int productGroupId) {
		return productRepository.selectProductsByGroupId(productGroupId);
	}
		
	// 상품 삭제하기(그룹)
	@Override
	public void deleteProductByGroupId(int productGroupId) {
		productRepository.deleteProductByGroupId(productGroupId);
	}

	// 상품ID로 상품 정보 갖고오기
	@Override
	public Product selectProductById(int productId) {
		return productRepository.selectProductById(productId);
	}
	
	// 상품 수정하기
	@Override
	public void updateProduct(Product product) {
		
		productRepository.updateProduct(product);
	}

	// 상품 삭제하기
	@Override
	public void deleteProduct(int productId) {
		productRepository.deleteProduct(productId);
	}
	
	// 상품명을 검색해서 해당 상품 정보 갖고오기
	@Override
	public List<ProductCheck> searchProductsByName(String searchText) {
		return productRepository.searchProductsByName(searchText);
	}

	
	// 주문 내역 조회하기(배송상태별)
	@Override
	public List<PurchaseList> selectPurchaseList(int commonCodeId, int offset, int pageSize) {
		return purchaseRepository.selectPurchaseList(commonCodeId, offset, pageSize);
	}

	// 주문 내역 개수 조회하기(전체, 배송상태별)
	@Override
	public int selectCountPurchaseList(int commonCodeId) {
		return purchaseRepository.selectCountPurchaseList(commonCodeId);
	}
	
	// 배송 상태 변경하기
	@Override
	public void changePurchaseStatus(int purchaseId, int commonCodeId) {
		purchaseRepository.changePurchaseStatus(purchaseId, commonCodeId);
	}

	// 주문 내역 검색하기(회원명, 주문번호 선택해서)
	@Override
	public List<PurchaseList> searchPurchaseInfo(String searchText, String searchOption, int offset, int pageSize) {
		return purchaseRepository.searchPurchaseInfo(searchText, searchOption, offset, pageSize);
	}

	// 검색 결과 개수 조회하기
	@Override
	public int selectCountPurchaseInfo(String searchText, String searchOption) {
		return purchaseRepository.selectCountPurchaseInfo(searchText, searchOption);
	}

	// 주문번호로 주문 상세 내역 조회하기
	@Override
	public List<PurchaseList> getPurchaseDetails(String purchaseNum) {
		return purchaseRepository.getPurchaseDetails(purchaseNum);
	}

	// 전체 카테고리 조회하기
	@Override
	public List<CategoryCheck> selectAllCategories(int offset, int pageSize) {
		return categoryRepository.selectAllCategories(offset, pageSize);
	}

	// 전체 카테고리 개수 조회하기
	@Override
	public int selectCountCategories() {
		return categoryRepository.selectCountCategories();
	}
	
	// 카테고리 삭제하기
	@Override
	public void deleteCategory(int categoryId) {
		categoryRepository.deleteCategory(categoryId);
	}

	// 카테고리ID로 카테고리 정보 조회하기
	@Override
	public CategoryCheck selectCategoryByCategoryId(int categoryId) {
		return categoryRepository.selectCategoryByCategoryId(categoryId);
	}

	// 카테고리 정보 수정하기
	@Override
	public void updateCategoryInfo(CategoryCheck categoryCheck) {
		categoryRepository.updateCategoryInfo(categoryCheck);
	}


	//카테고리 등록하기 ★
//	@Transactional
//	public void registerCategories(CategoryCheck categoryCheck, List<MultipartFile> files) {
//		List<String> imagePathList = s3Service.upload(files);
//		categoryCheck.setCategoryImage(imagePathList.get(0));
//
//		// 카테고리 등록 실패시 s3에 등록된 이미지 삭제
//		try {
//			categoryRepository.registerCategories(categoryCheck);
//			categoryCheck.getCategoryId();
//		} catch(Exception e) {
//			e.printStackTrace();
//			imagePathList.forEach((url) -> {
//				s3Service.deleteImage(url);	
//			});
//		}
//	}
	
	//테스트//
	@Transactional
	public void registerCategories(CategoryCheck categoryCheck) {
		categoryRepository.registerCategories(categoryCheck);
		
	}

	// 최상위 공통코드 조회하기
	@Override
	public List<CommonCode> selectAllCommonCodesPr(int offset, int pageSize) {
		return commonCodeRepository.selectAllCommonCodesPr(offset, pageSize);
	}

	// 최상위 공통코드 개수 조회하기
	@Override
	public int selectCountCommonCodesPr() {
		return commonCodeRepository.selectCountCommonCodesPr();
	}

	// 최상위 공통코드 삭제하기
	@Override
	public void deleteCommonCodePr(int commonCodeId) {
		commonCodeRepository.deleteCommonCodePr(commonCodeId);	
	}
	
	// 공통코드 조회하기(최상위 공통코드별)
	@Override
	public List<CommonCode> selectCommonCodesByPr(int commonCodeId) {
		return commonCodeRepository.selectCommonCodesByPr(commonCodeId);
	}

	// commonCodeId로 공통코드 정보 조회하기
	@Override
	public CommonCode selectCommonCode(int commonCodeId) {
		return commonCodeRepository.selectCommonCode(commonCodeId);
	}

	// 공통코드 수정하기
	@Override
	public void updateCommonCode(CommonCode commonCode) {
		commonCodeRepository.updateCommonCode(commonCode);
	}

	// 공통코드 삭제하기
	@Override
	public void deleteCommonCode(int commonCodeId) {
		commonCodeRepository.deleteCommonCode(commonCodeId);
	}

	
	
	@Override
	public void registerCommonCodes(CommonCode commonCode) {
		commonCodeRepository.registerCommonCodes(commonCode);
	}

	
	// 전체 쿠폰 조회하기
	@Override
	public List<CouponCheck> selectAllCoupons(int offset, int pageSize) {
		return couponRepository.selectAllCoupons(offset, pageSize);
	}

	// 전체 쿠폰 개수 조회하기
	@Override
	public int selectCountCoupons() {
		return couponRepository.selectCountCoupons();
	}

	// 쿠폰 삭제하기
	@Override
	public void deleteCoupon(int couponId) {
		couponRepository.deleteCoupon(couponId);		
	}

	// 쿠폰 등록하기
	@Override
	public void registerCoupons(CouponCheck couponCheck) {
		couponRepository.registerCoupons(couponCheck);		
	}

	//쿠폰 일련번호 중복 확인하기 (해당 쿠폰 일련번호가 데이터베이스에 이미 존재하는지 확인하고, 중복되지 않으면 true를 반환하고 중복된 경우 false를 반환)
	public boolean isCouponSnUnique(String couponSn) {
        int count = couponRepository.countByCouponSn(couponSn);
        return count == 0; // 0이면 중복되지 않음, 1 이상이면 중복됨
    }
	
}
