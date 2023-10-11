package com.example.daitso.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.SpecialProduct;
import com.example.daitso.product.repository.IProductRepository;


@Service
public class ProductService implements IProductService {
	
	@Autowired
	IProductRepository productRepository;

	@Override
	public List<Product> selectProductList(int categoryId, int page, String sort) {
		int start = (page-1)*16 + 1;
		return productRepository.selectProductList(categoryId, start, start+15, sort);
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
	public List<String> selectProductOptionFirst(int productGroupId) {
		return productRepository.selectProductOptionFirst(productGroupId);
	}

	@Override
	public List<String> selectProductOptionSecond(int productGroupId, String productOptionFirst) {
		return productRepository.selectProductOptionSecond(productGroupId, productOptionFirst);
	}

	@Override
	public List<String> selectProductOptionThird(int productGroupId, String productOptionFirst,
			String productOptionSecond) {
		return productRepository.selectProductOptionThird(productGroupId, productOptionFirst, productOptionSecond);

	}

	@Override
	public Product selectOptionProduct(int productGroupId, String productOptionFirst, String productOptionSecond,
			String productOptionThird) {
		return productRepository.selectOptionProduct(productGroupId, productOptionFirst, productOptionSecond, productOptionThird);
	}

	@Override
	public List<SpecialProduct> selectSpecialProduct() {
		return productRepository.selectSpecialProduct();
	}

	@Override
	public List<Product> saleProductList() {
		return productRepository.saleProductList();
	}
	
	//매 자정마다 인기상품 및 할인상품 업데이트 시켜주기
	 //@Scheduled(cron = "0 0 0 * * *")
	 @Scheduled(cron = "0 7 9 * * *")
	 @Transactional
	public void insertSpecialProduct() {
		List<Map<String,Integer>> list = productRepository.searchSpecialProduct("popular");
		productRepository.insertPopularProducts(list);
		
		List<Map<String,Integer>> sList = productRepository.searchSpecialProduct("sale");
		productRepository.updateSaleProducts(sList);

	}

	@Override
	public List<Product> selectSearchProduct(String searchText, int page, String sort) {
		int start = (page-1)*16 + 1;
		return productRepository.selectSearchProduct(searchText, start, start+15, sort);
	}

	@Override
	public int selectSearchProductCount(String searchText) {
		return productRepository.selectSearchProductCount(searchText);
	}

}
