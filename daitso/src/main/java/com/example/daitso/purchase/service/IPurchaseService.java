package com.example.daitso.purchase.service;

import java.util.List;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;

public interface IPurchaseService {
	void insertPurchase(Purchase purchase);
	List<PurchaseCheck> selectAllPurchase();
	void deletePurchase(Purchase purchase);

}
