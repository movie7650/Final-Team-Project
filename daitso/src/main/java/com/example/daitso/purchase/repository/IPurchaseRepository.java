package com.example.daitso.purchase.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.model.PurchaseList;

@Repository
@Mapper
public interface IPurchaseRepository {
	//구매하기 
	void insertPurchase(Purchase purchase);
	//구매상품이름가져오기
	List<PurchaseCheck> selectAllProductNM();
	//구매정보가져오기
	List<Purchase> selectAllPurchase();
	//주문취소 
	void canclePurchase(Purchase purchase);
	
	
	
//	List<PurchaseList> selectPurchaseList(@Param("commonCodeId") int commonCodeId, @Param("start") int start, @Param("end") int end);
	List<PurchaseList> selectPurchaseList(@Param("commonCodeId") int commonCodeId, @Param("offset") int offset, @Param("pageSize") int pageSize);
	int selectCountPurchaseList(@Param("commonCodeId") int commonCodeId);
	
	void changePurchaseStatus(@Param("purchaseId") int purchaseId, @Param("commonCodeId") int commonCodeId);
	
	
	List<PurchaseList> searchPurchaseInfo(String searchText);
	
}
