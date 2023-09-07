package com.example.daitso.product.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.product.model.Product;

@Mapper
@Repository
public interface IProductRepository {

	List<Product> selectProductList(@Param("categoryId") int categoryId, @Param("start") int start, @Param("end") int end);
	
	int selectCountProductList(@Param("categoryId") int categoryId);
	
	Product selectProduct(@Param("productId") int productId);

	void registerProducts(Product product);

	void changeProductCode();
	
	void deleteProduct(int productId);

	Product selectProductId(int productId);
	
	void updateProduct(Product product);
	
    List<Product> selectProducts(int firstCategoryId, int secondCategoryId, int offset, int pageSize);
    
    int selectCountProducts(int firstCategoryId, int secondCategoryId);
	
	List<String> selectProductOptionFirst(int productGroupId);
	
	List<String> selectProductOptionSecond(@Param("productGroupId") int productGroupId, @Param("productOptionFirst") String productOptionFirst);
	
	List<String> selectProductOptionThird(@Param("productGroupId") int productGroupId, @Param("productOptionFirst") String productOptionFirst, @Param("productOptionSecond") String productOptionSecond);

	Product selectOptionProduct(@Param("productGroupId") int productGroupId , @Param("productOptionFirst") String productOptionFirst, @Param("productOptionSecond") String productOptionSecond, @Param("productOptionThird") String productOptionThird);

	int selectInquiryProductId(@Param("productGroupId") int productGroupId, @Param("size") String size, @Param("color") String color, @Param("other") String other);

}

