package com.example.daitso.shipping.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.daitso.shipping.model.ShippingAdd;
import com.example.daitso.shipping.model.ShippingInfo;

public interface IShippingService {

	// 사용자 고유번호로부터 그에 해당하는 배송지 조회
	List<ShippingInfo> getShippingInfoByCustomerId(int customerId);
	
	// 배송지 고유번호로부터 그에 해당하는 배송지 조회
	ShippingInfo getShippingInfoByShippingId(int shippingId);
	
	// 배송지 추가
	void addShipping(ShippingAdd shippingAdd, int customerId);
	
	// 배송지 수정
	void updateShipping(ShippingAdd shippingUpdate, int shippingId, int customerId);
	
	// 배송지 삭제
	void deleteShipping(int shippingId);
}
