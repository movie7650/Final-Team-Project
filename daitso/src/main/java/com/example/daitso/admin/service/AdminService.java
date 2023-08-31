package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.repository.IProductRepository;

@Service
public class AdminService implements IAdminService{
	
	@Autowired
	IProductRepository productRepository;
	
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
	public List<Product> selectPagedProducts(int startRow, int endRow) {
		return productRepository.selectPagedProducts(startRow, endRow);
	}

	@Override
	public int getTotalProductCount() {
		return productRepository.getTotalProductCount();
	}

	@Override
	public List<Product> selectCategoryPagedProducts(int categoryId, int startRow, int endRow) {
		return productRepository.selectCategoryPagedProducts(categoryId,startRow, endRow);
	}

	@Override
	public int getCategoryTotalProductCount(int categoryId) {
		return productRepository.getCategoryTotalProductCount(categoryId);
	}

	@Override
	public List<Product> selectTopCategoryPagedProducts(int category_pr_id, int startRow, int endRow) {
		return productRepository.selectTopCategoryPagedProducts(category_pr_id, startRow, endRow);
	}

	@Override
	public int getTopCategoryTotalProductCount(int category_pr_id) {
		return productRepository.getTopCategoryTotalProductCount(category_pr_id);
	}
}
