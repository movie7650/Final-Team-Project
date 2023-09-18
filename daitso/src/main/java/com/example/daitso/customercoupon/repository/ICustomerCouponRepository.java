package com.example.daitso.customercoupon.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.example.daitso.customercoupon.model.SelectCustomerCoupon;
import org.apache.ibatis.annotations.Param;

import com.example.daitso.cart.model.CartCouponApply;


@Repository
@Mapper
public interface ICustomerCouponRepository {
	
	//사용가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectUsableCoupon();
	
	//사용불가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectBanCoupon();
	
	//사용자 쿠폰등록
	void insertCustomerCoupon();

	// 쿠폰 유효기간 지났는지 확인 -> 지나면 만료로 바꾸기
	void checkCouponEprDt();
	
	// 적용가능한 쿠폰 조회
	List<CartCouponApply> getCouponsByCustomerId(@Param("categoryId") int categoryId,@Param("customerId") int customerId);

}
