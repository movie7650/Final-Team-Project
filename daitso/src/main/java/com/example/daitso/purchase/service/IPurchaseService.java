package com.example.daitso.purchase.service;

import java.util.List;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;

public interface IPurchaseService {
	//구매
	void insertPurchase(Purchase purchase);
	//구매상품이름가져오기
	List<PurchaseCheck> selectAllProductNM();
	//구매상품정보가져오기
	List<Purchase> selectAllPurchase();
	//구매취소
	void canclePurchase(Purchase purchase);
	
}
