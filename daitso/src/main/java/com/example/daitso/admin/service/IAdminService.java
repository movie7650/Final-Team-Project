package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.product.model.Product;

public interface IAdminService {
	
	void registerProducts(Product product, List<MultipartFile> files);
	
	void deleteProduct(int productId);
	
	Product selectProductId(int productId);
	
	void updateProduct(Product product);
	
    List<Product> selectProducts(int firstCategoryId, int secondCategoryId, int offset, int pageSize);
    
    int selectCountProducts(int firstCategoryId, int secondCategoryId);

}
