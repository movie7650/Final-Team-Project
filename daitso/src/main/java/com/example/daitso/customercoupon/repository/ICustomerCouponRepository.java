package com.example.daitso.customercoupon.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.customercoupon.model.SelectCustomerCoupon;

@Repository
@Mapper
public interface ICustomerCouponRepository {
	
	//사용가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectUsableCoupon();
	//사용불가능한 쿠폰 리스트 출력
	List<SelectCustomerCoupon> selectBanCoupon();
	//사용자 쿠폰등록
	void insertCustomerCoupon();


}
