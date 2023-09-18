package com.example.daitso.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.repository.ICategoryRepository;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.product.repository.IProductRepository;
import com.example.daitso.purchase.model.PurchaseList;
import com.example.daitso.purchase.repository.IPurchaseRepository;
import com.example.daitso.purchase.service.IPurchaseService;

@Service
public class AdminService implements IAdminService{
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	ICategoryRepository categoryRepository;
	
	@Autowired
	IPurchaseRepository purchaseRepository;
	
	@Autowired
	S3Service s3Service;
	
	// 기존 상품 등록하기
//	@Transactional
//	public void registerExistingProducts(ProductCheck product, List<MultipartFile> files) {
//		List<String> imagePathList = s3Service.upload(files);
//		product.setProductImageFirst(imagePathList.get(0));
//		product.setProductImageSecond(imagePathList.get(1));
//		product.setProductImageThird(imagePathList.get(2));
//		
//		// 상품 등록 실패시 s3에 등록된 이미지 삭제
//		try {
//			productRepository.registerExistingProducts(product);
//			product.getProductId();
//			productRepository.changeProductCode();
//		} catch(Exception e) {
//			e.printStackTrace();
//			imagePathList.forEach((url) -> {
//				s3Service.deleteImage(url);	
//			});
//		}
//	}
	
	// 상품 조회하기(카테고리별)
	@Override
	public List<ProductCheck> selectProductsByCategory(int firstCategoryId, int secondCategoryId, int thirdCategoryId, int offset, int pageSize) {
		return productRepository.selectProductsByCategory(firstCategoryId, secondCategoryId, thirdCategoryId, offset, pageSize);
	}

	// 상품 개수 조회하기(카테고리별)
	@Override
	public int selectCountProducts(int firstCategoryId, int secondCategoryId, int thirdCategoryId) {
		return productRepository.selectCountProducts(firstCategoryId, secondCategoryId, thirdCategoryId);
	}
	
	// 상품 조회하기(그룹별)
	@Override
	public List<ProductCheck> selectProductsByGroupId(int productGroupId) {
		return productRepository.selectProductsByGroupId(productGroupId);
	}
		
	// 상품 삭제하기(그룹)
	@Override
	public void deleteProductByGroupId(int productGroupId) {
		productRepository.deleteProductByGroupId(productGroupId);
	}

	// 상품ID로 상품 정보 갖고오기
	@Override
	public Product selectProductId(int productId) {
		return productRepository.selectProductId(productId);
	}
	
	// 상품 수정하기
	@Override
	public void updateProduct(Product product) {
		productRepository.updateProduct(product);
	}

	// 상품 삭제하기
	@Override
	public void deleteProduct(int productId) {
		productRepository.deleteProduct(productId);
	}
	
	// 기존 상품 등록하기
	@Transactional
	public void registerExistingProducts(ProductCheck product) {
		productRepository.registerExistingProducts(product);
		
	}
	
	// 상품명을 검색해서 해당 상품 정보 갖고오기
	@Override
	public List<ProductCheck> searchProductsByName(String searchText) {
		return productRepository.searchProductsByName(searchText);
	}


	
	
	@Override
	public List<PurchaseList> selectPurchaseList(int commonCodeId, int offset, int pageSize) {
		return purchaseRepository.selectPurchaseList(commonCodeId, offset, pageSize);
	}

	@Override
	public int selectCountPurchaseList(int commonCodeId) {
		return purchaseRepository.selectCountPurchaseList(commonCodeId);
	}
	
	
	
	@Override
	public void changePurchaseStatus(int purchaseId, int commonCodeId) {
		purchaseRepository.changePurchaseStatus(purchaseId, commonCodeId);
	}

	@Override
	public List<PurchaseList> searchPurchaseInfo(String searchText) {
		return purchaseRepository.searchPurchaseInfo(searchText);
	}


	
}
