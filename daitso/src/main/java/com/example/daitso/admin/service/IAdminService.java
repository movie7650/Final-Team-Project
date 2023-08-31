package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.product.model.Product;

public interface IAdminService {
	
	void registerProducts(Product product, List<MultipartFile> files);
	
	void deleteProduct(int productId);
	
	Product selectProductId(int productId);
	
	void updateProduct(Product product);
	
	List<Product> selectPagedProducts(int startRow, int endRow);
	int getTotalProductCount();
	
	List<Product> selectCategoryPagedProducts(int categoryId, int startRow, int endRow);
	int getCategoryTotalProductCount(int categoryId);
	
	List<Product> selectTopCategoryPagedProducts(int category_pr_id, int startRow, int endRow);
	int getTopCategoryTotalProductCount(int category_pr_id);
}
