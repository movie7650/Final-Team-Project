package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;

public interface IAdminService {
	
    List<ProductCheck> selectProductsByCategory(int firstCategoryId, int secondCategoryId, int thirdCategoryId, int offset, int pageSize);
    
    int selectCountProducts(int firstCategoryId, int secondCategoryId, int thirdCategoryId);
   
	void deleteGroupProduct(int productGroupId);
    
	List<Product> selectProductsByGroupId(int productGroupId);
    
	ProductCheck selectProductId(int productId);
	void updateProduct(ProductCheck product);
	
	
	void registerExistingProducts(ProductCheck product, List<MultipartFile> files);
	
}
