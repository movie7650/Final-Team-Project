package com.example.daitso.purchase.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.product.model.ProductCheck;
import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.model.PurchaseDetailCheck;
import com.example.daitso.purchase.model.PurchaseList;

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
	
	
	
//	List<PurchaseList> selectPurchaseList(@Param("commonCodeId") int commonCodeId, @Param("start") int start, @Param("end") int end);
	List<PurchaseList> selectPurchaseList(@Param("commonCodeId") int commonCodeId, @Param("offset") int offset, @Param("pageSize") int pageSize);
	int selectCountPurchaseList(@Param("commonCodeId") int commonCodeId);

	//배송중인 상품 갯수
	int selectShipping(int customerId);
	
	//배송완료인 상품 갯수
	int selectShippingComplete(int customerId);
	
	//상세주문조회
	List<PurchaseDetailCheck> selectDetailPurchase(@Param("customerId") int customerId, @Param("purchaseNum")String purchaseNum);
	
	
	
	List<PurchaseList> selectAllPurchaseList();

	
	void changePurchaseStatus(@Param("purchaseId") int purchaseId, @Param("commonCodeId") int commonCodeId);
	
	
	List<PurchaseList> searchPurchaseInfo(String searchText);
	
}
