package com.example.daitso.purchase.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;

@Repository
@Mapper
public interface IPurchaseRepository {
	void insertPurchase(Purchase purchase);
	List<PurchaseCheck> selectAllPurchase();
	void deletePurchase(Purchase purchase);
	
}
