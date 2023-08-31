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
	public List<Product> selectProductList(int categoryId, int page) {
		int start = (page-1)*16 + 1;
		return productRepository.selectProductList(categoryId, start, start+15);
	}

	@Override
	public Product selectProduct(int productId) {
		return productRepository.selectProduct(productId);
	}

	@Override
	public int selectCountProductList(int categoryId) {
		return productRepository.selectCountProductList(categoryId);
	}

	@Override
	public List<String> selectProductOptionFirst(String productNm) {
		return productRepository.selectProductOptionFirst(productNm);
	}

	@Override
	public List<String> selectProductOptionSecond(String productNm, String productOptionFirst) {
		return productRepository.selectProductOptionSecond(productNm, productOptionFirst);
	}

	@Override
	public List<String> selectProductOptionThird(String productNm, String productOptionFirst,
			String productOptionSecond) {
		return productRepository.selectProductOptionThird(productNm, productOptionFirst, productOptionSecond);
	}


}
