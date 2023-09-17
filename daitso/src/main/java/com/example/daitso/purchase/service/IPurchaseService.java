package com.example.daitso.purchase.service;

import java.util.List;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;

public interface IPurchaseService {
	//구매
	void insertPurchase(Purchase purchase);
	//전체주문상품가져오기
	List<PurchaseCheck> selectAllOrderProduct(int customerId);
	//전체상품이름가져오기
	List<PurchaseCheck> selectAllProductNm(int customerId);
	//구매취소
	void canclePurchase(Purchase purchase);
	
}
