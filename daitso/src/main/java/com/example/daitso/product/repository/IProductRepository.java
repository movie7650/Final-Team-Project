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
	
	List<Product> selectAllProducts();
	
	Product selectProduct(@Param("productId") int productId);

	void registerProducts(Product product);

	void changeProductCode();
	
	void deleteProduct(int productId);

	Product selectProductId(int productId);
	
	void updateProduct(Product product);
	
	List<String> selectProductOptionFirst(String productNm);
	
	List<String> selectProductOptionSecond(@Param("productNm") String productNm, @Param("productOptionFirst") String productOptionFirst);
	
	List<String> selectProductOptionThird(@Param("productNm") String productNm, @Param("productOptionFirst") String productOptionFirst, @Param("productOptionSecond") String productOptionSecond);
}
