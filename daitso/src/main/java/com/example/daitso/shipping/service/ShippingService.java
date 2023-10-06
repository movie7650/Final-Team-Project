package com.example.daitso.shipping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.shipping.model.MypageReceiverShipping;
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
	//마이페이지의 배송지관리에서 보이는 내 배송지 리스트
	@Override
	public List<MypageReceiverShipping> selectMyShippingInfo(int customerId) {
		return shippingRepository.selectMyShippingInfo(customerId);
	}

	@Override
	public int selectShippingStatusY(int customerId) {
		return shippingRepository.selectShippingStatusY(customerId);
	}
	//마이페이지-배송지관리-배송지추가하기
	@Override
	public void insertMyshipping(int customerId, String shippingReceiverNM, String shippingRoadNMAddr,
			String shippingDaddr, String shippingReceiverTelNO, String shippingDmnd, int shippingDv) {
		shippingRepository.insertMyshipping(customerId, shippingReceiverNM, shippingRoadNMAddr, shippingDaddr, shippingReceiverTelNO, shippingDmnd, shippingDv);
	}
	//기본배송지 갯수 카운트
	@Override
	public int countShippingDv301(int customerId) {
		return shippingRepository.countShippingDv301(customerId);
	}
	//배송지 id에 따른 배송지 정보 가져오기
	@Override
	public List<MypageReceiverShipping> selectShippingIdInfo(int shippingId) {
		return shippingRepository.selectShippingIdInfo(shippingId);
	}
	//배송지id에 따른 배송지 정보 수정하기
	@Override
	public void updateShippingIdInfo(int shippingId, String shippingReceiverNM, String shippingRoadNMAddr,
			String shippingDaddr, String shippingReceiverTelNO, String shippingDmnd, int shippingDv) {
		shippingRepository.updateShippingIdInfo(shippingId, shippingReceiverNM, shippingRoadNMAddr, shippingDaddr, shippingReceiverTelNO, shippingDmnd, shippingDv);		
	}
	//배송지ID에 따른 배송지 삭제
	@Override
	public void deleteMyshipping(int shippingId) {
		shippingRepository.deleteMyshipping(shippingId);		
	}
	
	//배송지Id에 따른 배송지구분
	@Override
	public int selectShippingDv(int shipppingId) {
		return shippingRepository.selectShippingDv(shipppingId);
	}
	
	
}
