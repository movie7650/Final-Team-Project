package com.example.daitso.product.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.product.model.Product;

@Mapper
@Repository
public interface IProductService {
	
	List<Product> selectProductList(int categoryId);
	
	List<Product> selectAllProducts();
	
	Product selectProduct(int productId);
}
