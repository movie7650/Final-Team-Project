package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.product.model.Product;

public interface IAdminService {

//	List<Product> selectAllProducts();
	List<Product> selectAllProducts(int page, int pageSize);
	
	void registerProducts(Product product, List<MultipartFile> files);
	
	void deleteProduct(int productId);
	
	Product selectProductId(int productId);
	
	void updateProduct(Product product);
	
	List<Product> selectPagedProducts(int startRow, int endRow);
	int getTotalProductCount();
}
