package com.example.daitso.purchase.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.model.PurchaseInsert;
import com.example.daitso.purchase.model.PurchaseDetailCheck;

public interface IPurchaseService {
	
	//구매
	void insertPurchase(PurchaseInsert purchaseInsert);
	
	//전체주문상품가져오기
	List<PurchaseCheck> selectAllOrderProduct(int customerId);
	
	//전체상품이름가져오기
	List<PurchaseCheck> selectAllProductNm(int customerId);
	
	//구매취소
	void canclePurchase(Purchase purchase);
	
	//상세주문조회
	List<PurchaseDetailCheck> selectDetailPurchase(@Param("customerId")int customerId, @Param("purchaseNum")String purchaseNum);
	
	//배송중인 상품 갯수
	int selectShipping(int customerId);
		
	//배송완료인 상품 갯수
	int selectShippingComplete(int customerId);
	
	//입금/결제 상품 갯수
	int selectPayCoin(int customerId);
	
}
