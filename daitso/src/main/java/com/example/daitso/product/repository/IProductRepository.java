package com.example.daitso.product.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.product.model.SpecialProduct;

@Mapper
@Repository
public interface IProductRepository {

	// 상품 조회하기(카테고리별)(고객 페이지)
	List<Product> selectProductList(@Param("categoryId") int categoryId, @Param("start") int start, @Param("end") int end, @Param("sort") String sort);
	
	// 상품 개수 조회하기(카테고리별)(고객 페이지)
	int selectCountProductList(@Param("categoryId") int categoryId);
	
	// 상품 상세 정보 조회하기
	Product selectProduct(@Param("productId") int productId);

	// 상품 조회하기(카테고리별)
    List<ProductCheck> selectProductsByCategory(int firstCategoryId, int secondCategoryId, int thirdCategoryId, int offset, int pageSize);
    
    // 상품 개수 조회하기(카테고리별)
    int selectCountProducts(int firstCategoryId, int secondCategoryId, int thirdCategoryId);
    
    // 상품 조회하기(그룹ID별)
    List<ProductCheck> selectProductsByGroupId(int productGroupId);
    
    // 상품 삭제하기(그룹ID)
    void deleteProductByGroupId(int productGroupId);
	
	// 상품ID로 상품 정보 조회하기
	Product selectProductId(int productId);
	
	// 상품 수정하기
	void updateProduct(Product product);
	
	// 상품 삭제하기
    void deleteProduct(int productId);
    
    // 상품 등록하기
	void registerProducts(ProductCheck product);
	
	// 상품명을 검색해서 해당 상품 정보 갖고오기
	List<ProductCheck> searchProductsByName(String searchText);
	
	// 상품 첫 번째 옵션 조회
	List<String> selectProductOptionFirst(int productGroupId);
	
	// 상품 두 번째 옵션 조회
	List<String> selectProductOptionSecond(@Param("productGroupId") int productGroupId, @Param("productOptionFirst") String productOptionFirst);
	
	// 상품 세 번째 옵션 조회
	List<String> selectProductOptionThird(@Param("productGroupId") int productGroupId, @Param("productOptionFirst") String productOptionFirst, @Param("productOptionSecond") String productOptionSecond);

	// 옵션에 해당하는 상품 조회
	Product selectOptionProduct(@Param("productGroupId") int productGroupId , @Param("productOptionFirst") String productOptionFirst, @Param("productOptionSecond") String productOptionSecond, @Param("productOptionThird") String productOptionThird);

	// 특정 상품에 해당하는 문의글 조회
	int selectInquiryProductId(@Param("productGroupId") int productGroupId, @Param("size") String size, @Param("color") String color, @Param("other") String other);
	
	// 현재 날짜로 업데이트된 인기상품 조회
	List<SpecialProduct> selectSpecialProduct();
	
	// 할인상품 조회
	List<Product> saleProductList();
	
	// 인기상품, 할인상품 조회
	List<Map<String,Integer>> searchSpecialProduct(String selector);
	
	// 인기상품 삽입
	void insertPopularProducts(List<Map<String, Integer>> list);
	
	// 할인 상품 업데이트
	void updateSaleProducts(List<Map<String, Integer>> list);

	// 상품 검색
	List<Product> selectSearchProduct(@Param("searchText") String searchText, @Param("start") int start, @Param("end") int end, @Param("sort") String sort);
	
	// 상품 개수 조회(검색시)
	int selectSearchProductCount(@Param("searchText") String searchText);
}

