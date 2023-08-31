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

	@Override
	public List<PurchaseCheck> selectAllPurchase() {
		return purchaseRepository.selectAllPurchase();
	}

	@Override
	public void deletePurchase(Purchase purchase) {
		purchaseRepository.deletePurchase(purchase);		
	}

	@Override
	public void insertPurchase(Purchase purchase) {
		// TODO Auto-generated method stub
		 purchaseRepository.insertPurchase(purchase);
		
	}
	

}
