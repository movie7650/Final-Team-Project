package com.example.daitso.product.service;

import java.util.List;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.SpecialProduct;

public interface IProductService {
	
	// 상품 조회하기(카테고리별)(고객 페이지)
	List<Product> selectProductList(int categoryId, int start, String sort);
	
	// 상품 개수 조회하기(카테고리별)(고객 페이지)
	int selectCountProductList(int categoryId);
	
	// 상품 상세 정보 조회하기
	Product selectProduct(int productId);
	
	// 상품 첫 번째 옵션 조회
	List<String> selectProductOptionFirst(int productGroupId);
	
	// 상품 두 번째 옵션 조회
	List<String> selectProductOptionSecond(int productGroupId, String productOptionFirst);
	
	// 상품 세 번째 옵션 조회
	List<String> selectProductOptionThird(int productGroupId, String productOptionFirst, String productOptionSecond);

	// 옵션에 해당하는 상품 조회
	Product selectOptionProduct(int productGroupId, String productOptionFirst, String productOptionSecond, String productOptionThird);
	
	// 현재 날짜로 업데이트된 인기상품 조회
	List<SpecialProduct> selectSpecialProduct();
	
	// 할인상품 조회
	List<Product> saleProductList();
	
	// 인기상품 및 할인상품 업데이트
	void insertSpecialProduct();

}
