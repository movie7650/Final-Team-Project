package com.example.daitso.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public List<Product> selectAllProducts(int page, int pageSize) {
		return productRepository.selectAllProducts(page, pageSize);
	}

	@Override
	public Product selectProduct(int productId) {
		return productRepository.selectProduct(productId);
	}

	@Transactional
	public void registerProducts(Product product) {
		productRepository.registerProducts(product);
		productRepository.changeProductCode();
	}

	@Override
	public void deleteProduct(int productId) {
		productRepository.deleteProduct(productId);
	}

	@Override
	public void updateProduct(Product product) {
		productRepository.updateProduct(product);
	}

	@Override
	public Product selectProductId(int productId) {
		return productRepository.selectProductId(productId);
	}

	@Override
	public int selectCountProductList(int categoryId) {
		return productRepository.selectCountProductList(categoryId);
	}

	@Override
	 public List<Product> selectPagedProducts(int startRow, int endRow) {
        return productRepository.selectPagedProducts(startRow, endRow);
    }

	@Override
	public int getTotalProductCount() {
		return productRepository.getTotalProductCount();
	}


}
