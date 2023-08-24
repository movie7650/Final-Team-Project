package com.example.daitso.category.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.example.daitso.category.model.Category;

@Repository
@Mapper
public interface ICategoryRepository {
	
	// 상위 카테고리(식품,도서,...) 고유번호와 이름 갖고오기
	List<Category> getAllFirstCategoryIdAndName();
	
	// 상위 카테고리(식품,도서,...) 고유번호로 부터 하위 카테고리 고유번호와 이름 갖고오기
	List<Category> getSecondCategoryIdAndNameByFirstCategoryId(int categoryId);
	
	List<Category> selectCategoryList(@Param("categoryId") int categoryId);
}

