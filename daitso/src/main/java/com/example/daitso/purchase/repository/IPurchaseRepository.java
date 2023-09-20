package com.example.daitso.purchase.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.model.PurchaseInsert;
import com.example.daitso.purchase.model.PurchaseDetailCheck;
import com.example.daitso.purchase.model.PurchaseList;

@Repository
@Mapper
public interface IPurchaseRepository {
	
	//구매하기 
	void insertPurchase(PurchaseInsert purchaseInsert);
	
	//전체주문상품가져오기
	List<PurchaseCheck> selectAllOrderProduct(int customerId);
	
	//전체주문상품이름가져오기
	List<PurchaseCheck> selectAllProductNm(int customerId);
	
	//주문취소 
	void canclePurchase(Purchase purchase);
	
	//배송중인 상품 갯수
	int selectShipping(int customerId);
	
	//배송완료인 상품 갯수
	int selectShippingComplete(int customerId);
	
	//입금/결제상품갯수
	int selectPayCoin(int customerId);
	
	//주문번호 카운트 
	int selectPurchaseNumCount(int customerId);
	
	//상세주문조회
	List<PurchaseDetailCheck> selectDetailPurchase(@Param("customerId") int customerId, @Param("purchaseNum")String purchaseNum);

	
	List<PurchaseList> selectAllPurchaseList();

	// 주문 내역 조회하기(배송상태별)
	List<PurchaseList> selectPurchaseList(@Param("commonCodeId") int commonCodeId, @Param("offset") int offset, @Param("pageSize") int pageSize);
	
	// 주문 내역 개수 조회하기(배송상태별)
	int selectCountPurchaseList(@Param("commonCodeId") int commonCodeId);
	
	// 배송 상태 변경하기
	void changePurchaseStatus(@Param("purchaseId") int purchaseId, @Param("commonCodeId") int commonCodeId);
	
	// 주문 내역 검색하기(회원명, 주문번호 선택해서)
//	List<PurchaseList> searchPurchaseInfo(String searchText, String searchOption);
	List<PurchaseList> searchPurchaseInfo(String searchText, String searchOption,@Param("offset") int offset, @Param("pageSize") int pageSize);

	// 검색 결과 개수 조회하기
	int selectCountPurchaseInfo(String searchText, String searchOption);
	
	// 주문 내역 정보 갖고오기
	List<PurchaseList> getPurchaseDetails(String purchaseNum);
}
