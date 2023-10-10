package com.example.daitso.shipping.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.shipping.model.MypageReceiverShipping;
import com.example.daitso.shipping.model.ShippingAdd;
import com.example.daitso.shipping.model.ShippingInfo;

@Repository
@Mapper
public interface IShippingRepository {
	
	//status가 Y 인거 갯수 조회
	int selectShippingStatusY(int customerId);
	
	//마이페이지-배송지관리에 보이는 배송지 목록출력
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
	
	// 사용자 고유번호로부터 그에 해당하는 배송지 조회
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
	void addShipping(@Param("shippingAdd") ShippingAdd shippingAdd, @Param("customerId") int customerId);
	
	// 배송지 수정
	void updateShipping(@Param("shippingUpdate") ShippingAdd shippingUpdate, @Param("shippingId") int shippingId, @Param("customerId") int customerId);
	
	// 배송지 삭제
	void deleteShipping(@Param("shippingId") int shippingId,@Param("customerId") int customerId);
	
	// 301인 상태인 배송지 아이디 조회
	int countShippingId301(int customerId);
	
	//shippingDv변경
	void updateShippingDV(@Param("shippingId")int shippingId, @Param("shippingDv") int shippingDv);
	
	//모든 배송지를 일반으로 변경
	void updateAllShippingDv302(int customerId);
}
