package com.example.daitso.product.service;

import java.util.List;

import com.example.daitso.product.model.Product;

public interface IProductService {
	
	List<Product> selectProductList(int categoryId);

}
