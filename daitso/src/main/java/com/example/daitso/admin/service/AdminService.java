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
		productRepository.registerProducts(product);
		
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
	public Product selectProductId(int productId) {
		return productRepository.selectProductId(productId);
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


	//카테고리 등록하기
	@Transactional
	public void registerCategories(CategoryCheck categoryCheck, List<MultipartFile> files) {
		List<String> imagePathList = s3Service.upload(files);
		categoryCheck.setCategoryImage(imagePathList.get(0));

		// 카테고리 등록 실패시 s3에 등록된 이미지 삭제
		try {
			categoryRepository.registerCategories(categoryCheck);
			categoryCheck.getCategoryId();
		} catch(Exception e) {
			e.printStackTrace();
			imagePathList.forEach((url) -> {
				s3Service.deleteImage(url);	
			});
		}
	}

	@Override
	public List<CommonCode> selectAllCommonCodes(int offset, int pageSize) {
		return commonCodeRepository.selectAllCommonCodes(offset, pageSize);
	}

	@Override
	public int selectCountCommonCodes() {
		return commonCodeRepository.selectCountCommonCodes();
	}
	
	
	
}
