package com.example.daitso.shipping.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.shipping.model.ShippingAdd;
import com.example.daitso.shipping.model.ShippingInfo;

@Repository
@Mapper
public interface IShippingRepository {
	
	// 사용자 고유번호로부터 그에 해당하는 배송지 조회
	List<ShippingInfo> getShippingInfoByCustomerId(int customerId);
	
	// 사용자 고유번호로부터 그에 해당하는 배송지 조회
	ShippingInfo getShippingInfoByShippingId(int shippingId);
	
	// 배송지 추가
	void addShipping(@Param("shippingAdd") ShippingAdd shippingAdd, @Param("customerId") int customerId);
	
	// 배송지 수정
	void updateShipping(@Param("shippingUpdate") ShippingAdd shippingUpdate, @Param("shippingId") int shippingId, @Param("customerId") int customerId);
	
	// 배송지 삭제
	void deleteShipping(int shippingId);
}
