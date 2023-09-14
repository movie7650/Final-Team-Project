package com.example.daitso.customercoupon.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.cart.model.CartCouponApply;

@Repository
@Mapper
public interface ICustomerCouponRepository {

	// 쿠폰 유효기간 지났는지 확인 -> 지나면 만료로 바꾸기
	void checkCouponEprDt();
	
	// 적용가능한 쿠폰 조회
	List<CartCouponApply> getCouponsByCustomerId(@Param("categoryId") int categoryId,@Param("customerId") int customerId);
}
