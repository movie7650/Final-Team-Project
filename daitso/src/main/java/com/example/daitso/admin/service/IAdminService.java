package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductShow;

public interface IAdminService {
	
    List<ProductShow> selectProducts(int firstCategoryId, int secondCategoryId, int offset, int pageSize);
    
    int selectCountProducts(int firstCategoryId, int secondCategoryId);
   
	void deleteProduct(int productGroupId);
    
	List<Product> selectProductsByGroupId(int productGroupId);
    
    
    List<Product> selectProductDetails(int productGroupId);
	void registerExistingProducts(ProductShow product, List<MultipartFile> files);
	Product selectProductId(int productId);
	void updateProduct(Product product);
}
