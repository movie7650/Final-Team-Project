package com.example.daitso.product.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;

@Mapper
@Repository
public interface IProductRepository {

	List<Product> selectProductList(@Param("categoryId") int categoryId, @Param("start") int start, @Param("end") int end);
	
	int selectCountProductList(@Param("categoryId") int categoryId);
	
	Product selectProduct(@Param("productId") int productId);

	
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
	void registerExistingProducts(ProductCheck product);
	
	/* void makeProductCode(); */
	
	
	List<String> selectProductOptionFirst(int productGroupId);
	
	List<String> selectProductOptionSecond(@Param("productGroupId") int productGroupId, @Param("productOptionFirst") String productOptionFirst);
	
	List<String> selectProductOptionThird(@Param("productGroupId") int productGroupId, @Param("productOptionFirst") String productOptionFirst, @Param("productOptionSecond") String productOptionSecond);

	Product selectOptionProduct(@Param("productGroupId") int productGroupId , @Param("productOptionFirst") String productOptionFirst, @Param("productOptionSecond") String productOptionSecond, @Param("productOptionThird") String productOptionThird);

	int selectInquiryProductId(@Param("productGroupId") int productGroupId, @Param("size") String size, @Param("color") String color, @Param("other") String other);

}

