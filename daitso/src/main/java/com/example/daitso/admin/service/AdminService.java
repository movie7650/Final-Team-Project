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
		product.setProductImageFirst(imagePathList.get(0));
		product.setProductImageSecond(imagePathList.get(1));
		product.setProductImageThird(imagePathList.get(2));
		
		// 상품 등록 실패시 s3에 등록된 이미지 삭제
		try {
			productRepository.registerProducts(product);
			product.getProductId();
			productRepository.changeProductCode();
		} catch(Exception e) {
			e.printStackTrace();
			imagePathList.forEach((url) -> {
				s3Service.deleteImage(url);	
			});
		}
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
