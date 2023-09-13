package com.example.daitso.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.repository.IPurchaseRepository;

@Service
public class PurchaseService implements IPurchaseService {
	@Autowired
	IPurchaseRepository purchaseRepository;
	//구매상품이름가져오기
	@Override
	public List<PurchaseCheck> selectAllProductNM() {
		return purchaseRepository.selectAllProductNM();
	}
	//구매취소
	@Override
	public void canclePurchase(Purchase purchase) {
		purchaseRepository.canclePurchase(purchase);		
	}
	//구매
	@Override
	public void insertPurchase(Purchase purchase) {
		 purchaseRepository.insertPurchase(purchase);
		
	}
	//구매상품정보가져오기
	@Override
	public List<Purchase> selectAllPurchase() {
			return purchaseRepository.selectAllPurchase();
	}

}
