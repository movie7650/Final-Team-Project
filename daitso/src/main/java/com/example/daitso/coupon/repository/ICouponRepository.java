package com.example.daitso.coupon.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.coupon.model.CouponCheck;

@Repository
@Mapper
public interface ICouponRepository {

	//쿠폰
	List<CouponCheck> selectAllCoupons(@Param("offset") int offset, @Param("pageSize") int pageSize);
	
	//쿠폰
	int selectCountCoupons();
	
	
	// 
	void deleteCoupon(int couponId);
	
}
