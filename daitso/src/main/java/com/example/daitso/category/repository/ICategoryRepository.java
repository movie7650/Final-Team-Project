package com.example.daitso.category.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.example.daitso.category.model.Category;
import com.example.daitso.category.model.CategoryCheck;


@Repository
@Mapper
public interface ICategoryRepository {
	
	// 상위 카테고리(식품,도서,...) 고유번호와 이름 갖고오기
	List<Category> getAllFirstCategoryIdAndName();
	
	// 상위 카테고리(식품,도서,...) 고유번호로 부터 하위 카테고리 고유번호와 이름 갖고오기
	List<Category> getSecondCategoryIdAndNameByFirstCategoryId(int categoryId);
	
	// 선택된 카테고리를 기준으로 하위 카테고리들을 계층형으로 갖고오기
	List<Category> selectCategoryList(@Param("categoryId") int categoryId);
	
	// 선택된 카테고리의 경로 갖고오기
	String selectCategoryPath(@Param("categoryId") int categoryId);
	
	// 전체 카테고리를 계층형으로 갖고오기
	List<Category> selectAllCategory();
	
	// 카테고리 계층 업데이트
	void updateCategory(@Param("categoryId") int categoryId, @Param("parentCategoryId") int parentCategoryId);
	
	// 카테고리 무작위 8개 갖고오기
	List<Category> selectMainCategory();
	
	// 전체 카테고리 조회하기
	List<CategoryCheck> selectAllCategories(@Param("offset") int offset, @Param("pageSize") int pageSize);
	
	// 전체 카테고리 개수 조회하기
	int selectCountCategories();
	
	// 카테고리 삭제하기
	void deleteCategory(int categoryId);
	
	// 카테고리ID로 카테고리 정보 조회하기
	CategoryCheck selectCategoryByCategoryId(int categoryId);
	
	// 카테고리 정보 수정하기
	void updateCategoryInfo(CategoryCheck categoryCheck);
	
	// 카테고리 등록하기
	void registerCategories(CategoryCheck categoryCheck);

	void updateCategoryImage(int categoryId, String imageUrl);
	
	void deleteCategoryImage(int categoryId, boolean deleteCategoryImage);
}

