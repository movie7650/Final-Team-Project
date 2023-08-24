package com.example.daitso.category.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.category.model.Category;

@Mapper
@Repository
public interface ICategoryRepository {
	List<Category> selectCategoryList(@Param("categoryId") int categoryId);
}
