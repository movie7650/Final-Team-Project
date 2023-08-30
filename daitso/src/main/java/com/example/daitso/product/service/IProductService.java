package com.example.daitso.product.service;

import java.util.List;

import com.example.daitso.product.model.Product;

public interface IProductService {
	
	List<Product> selectProductList(int categoryId, int start);
	
	int selectCountProductList(int categoryId);
	
	
//	List<Product> selectAllProducts();
	List<Product> selectAllProducts(int page, int pageSize);
	
	Product selectProduct(int productId);
	
	void registerProducts(Product product);
	
	void deleteProduct(int productId);
	
	Product selectProductId(int productId);
	void updateProduct(Product product);
	
	List<Product> selectPagedProducts(int startRow, int endRow);
	int getTotalProductCount();
}
