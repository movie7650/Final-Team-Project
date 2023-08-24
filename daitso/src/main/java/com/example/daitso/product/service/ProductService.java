package com.example.daitso.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.repository.IProductRepository;


@Service
public class ProductService implements IProductService {
	
	@Autowired
	IProductRepository productRepository;

	@Override
	public List<Product> selectProductList(int categoryId) {
		return productRepository.selectProductList(categoryId);
	}
	
	@Override
	public List<Product> selectAllProducts() {
		return productRepository.selectAllProducts();
	}

	@Override

	public Product selectProduct(int productId) {
		return productRepository.selectProduct(productId);
	}

	public void registerProducts(Product product) {
		productRepository.registerProducts(product);
	}

}
