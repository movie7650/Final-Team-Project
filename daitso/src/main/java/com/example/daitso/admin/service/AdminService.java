package com.example.daitso.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.repository.ICategoryRepository;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.product.repository.IProductRepository;

@Service
public class AdminService implements IAdminService{
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	ICategoryRepository categoryRepository;
	
	@Autowired
	S3Service s3Service;
	
	@Transactional
	public void registerExistingProducts(ProductCheck product, List<MultipartFile> files) {
		List<String> imagePathList = s3Service.upload(files);
		product.setProductImageFirst(imagePathList.get(0));
		product.setProductImageSecond(imagePathList.get(1));
		product.setProductImageThird(imagePathList.get(2));
		
		// 상품 등록 실패시 s3에 등록된 이미지 삭제
		try {
			productRepository.registerExistingProducts(product);
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
	public List<ProductCheck> selectProductsByCategory(int firstCategoryId, int secondCategoryId, int thirdCategoryId, int offset, int pageSize) {
		return productRepository.selectProductsByCategory(firstCategoryId, secondCategoryId, thirdCategoryId, offset, pageSize);
	}

	@Override
	public int selectCountProducts(int firstCategoryId, int secondCategoryId, int thirdCategoryId) {
		return productRepository.selectCountProducts(firstCategoryId, secondCategoryId, thirdCategoryId);
	}

	
	@Override
	public void deleteGroupProduct(int productGroupId) {
		productRepository.deleteGroupProduct(productGroupId);
	}
	
	@Override
	public List<Product> selectProductsByGroupId(int productGroupId) {
		return productRepository.selectProductsByGroupId(productGroupId);
	}

	@Override
	public void updateProduct(Product product) {
		productRepository.updateProduct(product);
	}

	@Override
	public Product selectProductId(int productId) {
		return productRepository.selectProductId(productId);
	}

}
