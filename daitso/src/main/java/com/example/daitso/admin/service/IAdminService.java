package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;

public interface IAdminService {
	
	// 카테고리별 상품 조회하기
    List<ProductCheck> selectProductsByCategory(int firstCategoryId, int secondCategoryId, int thirdCategoryId, int offset, int pageSize);
    
    // 카테고리별 상품 개수 조회하기
    int selectCountProducts(int firstCategoryId, int secondCategoryId, int thirdCategoryId);
   
    // 그룹 상품 삭제하기
	void deleteGroupProduct(int productGroupId);
    
	// 그룹별 상품 조회하기
	List<ProductCheck> selectProductsByGroupId(int productGroupId);
    
	// 해당 상품 불러오기
	Product selectProductId(int productId);
	
	// 해당 상품 수정하기
	void updateProduct(Product product);
	
	// 해당 상품 삭제하기
	void deleteProduct(int productId);
	
	//기존 상품 등록하기
//	void registerExistingProducts(ProductCheck product, List<MultipartFile> files);
	
	//기존 상품 등록하기
	void registerExistingProducts(ProductCheck product);
}
