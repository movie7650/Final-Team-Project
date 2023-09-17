package com.example.daitso.purchase.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;

@Repository
@Mapper
public interface IPurchaseRepository {
	//구매하기 
	void insertPurchase(Purchase purchase);
	//전체주문상품가져오기
	List<PurchaseCheck> selectAllOrderProduct(int customerId);
	//전체주문상품이름가져오기
	List<PurchaseCheck> selectAllProductNm(int customerId);
	//주문취소 
	void canclePurchase(Purchase purchase);
	
}
