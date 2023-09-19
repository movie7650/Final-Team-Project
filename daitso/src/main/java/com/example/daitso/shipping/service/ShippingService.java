package com.example.daitso.shipping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.shipping.model.ShippingAdd;
import com.example.daitso.shipping.model.ShippingInfo;
import com.example.daitso.shipping.repository.IShippingRepository;

@Service
public class ShippingService implements IShippingService {

	@Autowired
	IShippingRepository shippingRepository;
	
	// 사용자 고유번호로부터 그에 해당하는 배송지 조회
	@Override
	public List<ShippingInfo> getShippingInfoByCustomerId(int customerId) {
		return shippingRepository.getShippingInfoByCustomerId(customerId);
	}
	
	// 배송지 고유번호로부터 그에 해당하는 배송지 조회
	@Override
	public ShippingInfo getShippingInfoByShippingId(int shippingId) {
		return shippingRepository.getShippingInfoByShippingId(shippingId);
	}
	
	// 배송지 추가
	@Transactional
	public void addShipping(ShippingAdd shippingAdd, int customerId) {
		shippingRepository.addShipping(shippingAdd, customerId);
	}

	// 배송지 수정
	@Override
	public void updateShipping(ShippingAdd shippingUpdate, int shippingId, int customerId) {
		shippingRepository.updateShipping(shippingUpdate, shippingId, customerId);
	}

	// 배송지 삭제
	@Override
	public void deleteShipping(int shippingId, int customerId) {
		shippingRepository.deleteShipping(shippingId, customerId);
	}
}
