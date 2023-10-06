package com.example.daitso.shipping.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.daitso.shipping.model.MypageReceiverShipping;
import com.example.daitso.shipping.model.ShippingAdd;
import com.example.daitso.shipping.model.ShippingInfo;

public interface IShippingService {
	
	//status가 Y 인거 갯수 조회
	int selectShippingStatusY(int customerId);
	
	//마이페이지의 배송지관리에 보이는 배송지 목록출력
	List<MypageReceiverShipping> selectMyShippingInfo(int customerId);
	
	//shippingId에 따른 배송정보 가져오기
	List<MypageReceiverShipping> selectShippingIdInfo(@Param("shippingId")int shippingId);
	
	//shipppingId에 따라 가져온 배송정보 수정하기
	void updateShippingIdInfo(@Param("shippingId")int shippingId, @Param("shippingReceiverNM") String shippingReceiverNM,
							  @Param("shippingRoadNMAddr") String shippingRoadNMAddr, @Param("shippingDaddr") String shippingDaddr,
							  @Param("shippingReceiverTelNO") String shippingReceiverTelNO, @Param("shippingDmnd") String shippingDmnd,
							  @Param("shippingDv") int shippingDv);

	// 사용자 고유번호로부터 그에 해당하는 배송지 조회
	List<ShippingInfo> getShippingInfoByCustomerId(int customerId);
	
	// 배송지 고유번호로부터 그에 해당하는 배송지 조회
	ShippingInfo getShippingInfoByShippingId(int shippingId);
	
	//마이페이지-배송지관리-배송지추가InsertMyshippingAddress
	void insertMyshipping(@Param("customerId") int customerId, @Param("shippingReceiverNM") String shippingReceiverNM,
						  @Param("shippingRoadNMAddr") String shippingRoadNMAddr, @Param("shippingDaddr") String shippingDaddr,
						  @Param("shippingReceiverTelNO") String shippingReceiverTelNO, @Param("shippingDmnd") String shippingDmnd,
						  @Param("shippingDv") int shippingDv);
	
	//배송지 Id에 따른 배송지구분 가져오기 
	int selectShippingDv(@Param("shippingId") int shipppingId);
	
	//마이페이지-배송지관리-배송지 삭제
	void deleteMyshipping(@Param("shippingId")int shippingId);
	
	//shippingDv가 301(기본배송지) 인거 카운트 
	int countShippingDv301(int customerId);
	
	// 배송지 추가
	void addShipping(ShippingAdd shippingAdd, int customerId);
	
	// 배송지 수정
	void updateShipping(ShippingAdd shippingUpdate, int shippingId, int customerId);
	
	// 배송지 삭제
	void deleteShipping(int shippingId, int customerId);
}
