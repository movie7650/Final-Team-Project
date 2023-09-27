package com.example.daitso.coupon.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.coupon.model.CouponCheck;

@Repository
@Mapper
public interface ICouponRepository {

	// 전체 쿠폰 조회하기 
	List<CouponCheck> selectAllCoupons(@Param("offset") int offset, @Param("pageSize") int pageSize);
	
	// 전체 쿠폰 개수 조회하기
	int selectCountCoupons();
	
	// 쿠폰 삭제하기
	void deleteCoupon(int couponId);
	
	// 쿠폰 등록하기
	void registerCoupons(CouponCheck couponCheck);
	
	// 쿠폰 일련번호 중복 확인을 위한 개수 조회하기
	int countByCouponSn(String couponSn);
}
