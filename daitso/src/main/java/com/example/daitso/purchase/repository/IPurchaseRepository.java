package com.example.daitso.purchase.repository;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseChart;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.model.PurchaseDetailCheck;
import com.example.daitso.purchase.model.PurchaseInsert;
import com.example.daitso.purchase.model.PurchaseList;

@Repository
@Mapper
public interface IPurchaseRepository {

	// 구매하기
	void insertPurchase(PurchaseInsert purchaseInsert);

	// 전체주문상품가져오기
	List<PurchaseCheck> selectAllOrderProduct(@Param("customerId")int customerId, @Param("start")int start, @Param("end")int end); 

	// purchase_dv가 401(입금/결제) 인 상품 가져오기
	List<PurchaseCheck> selectPurchaseDv401(@Param("customerId")int customerId, @Param("start")int start, @Param("end")int end);

	// purchase_dv가 402(배송중) 인 상품 가져오기
	List<PurchaseCheck> selectPurchaseDv402(@Param("customerId")int customerId, @Param("start")int start, @Param("end")int end);

	// purchase_dv가 403(배송완료) 인 상품 가져오기
	List<PurchaseCheck> selectPurchaseDv403(@Param("customerId")int customerId, @Param("start")int start, @Param("end")int end);

	// 전체주문상품이름가져오기
	List<PurchaseCheck> selectAllProductNm(int customerId);

	// 주문취소
	void canclePurchase(Purchase purchase);

	// 내 주문상품 전체 갯수
	int countMyOrderList(int customerId);

	// 배송중인 상품 갯수
	int selectShipping(int customerId);

	// 배송완료인 상품 갯수
	int selectShippingComplete(int customerId);

	// 입금/결제상품갯수
	int selectPayCoin(int customerId);

	// 주문번호 카운트
	int selectPurchaseNumCount(int customerId);

	// 상세주문조회
	List<PurchaseDetailCheck> selectDetailPurchase(@Param("customerId") int customerId,
			@Param("purchaseNum") String purchaseNum);

	// 전체 주문 내역 조회하기
	List<PurchaseList> selectAllPurchaseList();

	// 주문 내역 조회하기(배송상태별)
	List<PurchaseList> selectPurchaseList(@Param("commonCodeId") int commonCodeId, @Param("offset") int offset,
			@Param("pageSize") int pageSize);

	// 주문 내역 개수 조회하기(전체, 배송상태별)
	int selectCountPurchaseList(@Param("commonCodeId") int commonCodeId);

	// 배송 상태 변경하기
	void changePurchaseStatus(@Param("purchaseId") int purchaseId, @Param("commonCodeId") int commonCodeId);

	// 주문 내역 검색하기(회원명, 주문번호 선택해서)
	List<PurchaseList> searchPurchaseInfo(String searchText, String searchOption, @Param("offset") int offset,
			@Param("pageSize") int pageSize);

	// 검색 결과 개수 조회하기
	int selectCountPurchaseInfo(String searchText, String searchOption);

	// 주문번호로 주문 상세 내역 조회하기
	List<PurchaseList> getPurchaseDetails(String purchaseNum);

	// 상품구매시 제고 업테이트
	void updateProductStockInPurchase(int cartId);

	// 일일(현재 날짜를 기준으로 7일치), 주간(현재 날짜를 기준으로 4주치), 월별(현재 날짜를 기준으로 5개월치) 매출액과 주문량 조회하기
	List<PurchaseChart> selectSalesStatus(@Param("dateType") String dateType);

	// 당일, 금주, 당월 가장 많이 팔린 상품 상위 5개 조회하기
	List<PurchaseChart> selectTopSelling(@Param("dateType") String dateType);

	int selectCountPurchaseDv(@Param("purchaseDv") int purchaseDv);

}
