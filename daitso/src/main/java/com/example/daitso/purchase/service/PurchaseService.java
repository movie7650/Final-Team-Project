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
	//전체주문상품가져오기
	@Override
	public List<PurchaseCheck> selectAllOrderProduct(int customerId) {
		return purchaseRepository.selectAllOrderProduct(customerId);
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
	@Override
	public List<PurchaseCheck> selectAllProductNm(int customerId) {
		// TODO Auto-generated method stub
		return purchaseRepository.selectAllProductNm(customerId);
	}
}
