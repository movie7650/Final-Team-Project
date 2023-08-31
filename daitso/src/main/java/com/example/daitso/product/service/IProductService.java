package com.example.daitso.product.service;

import java.util.List;

import com.example.daitso.product.model.Product;

public interface IProductService {
	
	List<Product> selectProductList(int categoryId, int start);
	
	int selectCountProductList(int categoryId);
	
	Product selectProduct(int productId);
	
	List<String> selectProductOptionFirst(String productNm);
	
	List<String> selectProductOptionSecond(String productNm, String productOptionFirst);
	
	List<String> selectProductOptionThird(String productNm, String productOptionFirst, String productOptionSecond);

	List<Product> selectPagedProducts(int startRow, int endRow);
	
	int getTotalProductCount();

	Product selectOptionProduct(String productNm, String productOptionFirst, String productOptionSecond, String productOptionThird);

}
