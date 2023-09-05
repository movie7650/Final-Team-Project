package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.repository.IProductRepository;

@Service
public class AdminService implements IAdminService{
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	S3Service s3Service;
	
	@Transactional
	public void registerProducts(Product product, List<MultipartFile> files) {
		List<String> imagePathList = s3Service.upload(files);
		
		// 이거는 일단 이렇게 함...
		if(imagePathList.size() == 1) {
			product.setProductImageFirst(imagePathList.get(0));
			product.setProductImageSecond("0");
			product.setProductImageThird("0");
		} else if(imagePathList.size() == 2) {
			product.setProductImageFirst(imagePathList.get(0));
			product.setProductImageSecond(imagePathList.get(1));
			product.setProductImageThird("0");
		} else {
			product.setProductImageFirst(imagePathList.get(0));
			product.setProductImageSecond(imagePathList.get(1));
			product.setProductImageThird(imagePathList.get(2));
		}

		productRepository.registerProducts(product);
		
		
		product.getProductId();
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
	
	/*
	 * @Override public List<Product> selectAllProducts() { return
	 * productRepository.selectAllProducts(); }
	 */

	@Override
	public List<Product> selectProducts(int firstCategoryId, int secondCategoryId) {
	    return productRepository.selectProducts(firstCategoryId, secondCategoryId);
	}

	
}
