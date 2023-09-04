package com.example.daitso.cart.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ICartRepository {
	void insertCart(@Param("productId") int productId, @Param("customerId") int customerId, @Param("productCnt") int productCnt);
}
