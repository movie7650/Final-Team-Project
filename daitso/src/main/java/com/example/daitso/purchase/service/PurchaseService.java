package com.example.daitso.purchase.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.cart.repository.ICartRepository;
import com.example.daitso.customercoupon.repository.ICustomerCouponRepository;
import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.model.PurchaseDetailCheck;
import com.example.daitso.purchase.model.PurchaseInsert;
import com.example.daitso.purchase.repository.IPurchaseRepository;

@Service
public class PurchaseService implements IPurchaseService {

	@Autowired
	IPurchaseRepository purchaseRepository;

	@Autowired
	ICartRepository cartRepository;

	@Autowired
	ICustomerCouponRepository customerCouponRepository;

	// 전체주문상품가져오기
	@Override
	public List<PurchaseCheck> selectAllOrderProduct(int customerId, int page) {
		int start = (page-1)*3 + 1;
		return purchaseRepository.selectAllOrderProduct(customerId,start,start+2);
	} // 서비스

	// 구매취소
	@Override
	public void canclePurchase(Purchase purchase) {
		purchaseRepository.canclePurchase(purchase);
	}

	// 구매
	@Transactional
	public void insertPurchase(PurchaseInsert purchaseInsert) {
		purchaseRepository.insertPurchase(purchaseInsert);
		purchaseRepository.updateProductStockInPurchase(purchaseInsert.getCartId());
		cartRepository.updateCartStatusPurchaseSuccess(purchaseInsert.getCustomerId(), purchaseInsert.getCartId());
		if (purchaseInsert.getCustomerCouponId() != 0) {
			customerCouponRepository.updateCustomerCouponStatusPurchaseSuccess(purchaseInsert.getCustomerId(),
					purchaseInsert.getCustomerCouponId());
		}
	}

	@Override
	public List<PurchaseCheck> selectAllProductNm(int customerId) {
		return purchaseRepository.selectAllProductNm(customerId);
	}

	@Override
	public int selectShipping(int customerId) {
		return purchaseRepository.selectShipping(customerId);

	}

	@Override
	public int selectShippingComplete(int customerId) {
		return purchaseRepository.selectShippingComplete(customerId);

	}

	@Override
	public List<PurchaseDetailCheck> selectDetailPurchase(int customerId, String purchaseNum) {
		// TODO Auto-generated method stub
		return purchaseRepository.selectDetailPurchase(customerId, purchaseNum);
	}

	@Override
	public int selectPayCoin(int customerId) {
		return purchaseRepository.selectPayCoin(customerId);
	}

	@Override
	public int selectPurchaseNumCount(int customerId) {
		return purchaseRepository.selectPurchaseNumCount(customerId);
	}

	// 내 주문상품 전체 갯수
	@Override
	public int countMyOrderList(int customerId) {
		return purchaseRepository.countMyOrderList(customerId);
	}

	// purchase_dv가 401(입금/결제)인 상품 가져오기
	@Override
	public List<PurchaseCheck> selectPurchaseDv401(int customerId, int page) {
		int start = (page-1)*3 + 1;
		return purchaseRepository.selectPurchaseDv401(customerId,start, start+2);
	}

	// purchase_dv가 402(배송중) 인 상품 가져오기
	@Override
	public List<PurchaseCheck> selectPurchaseDv402(int customerId, int page) {
		int start = (page-1)*3 + 1;
		return purchaseRepository.selectPurchaseDv402(customerId,start, start+2);
	}

	// purchase_dv가 403(배송완료) 인 상품 가져오기
	@Override
	public List<PurchaseCheck> selectPurchaseDv403(int customerId, int page) {
		int start = (page-1)*3 + 1;
		return purchaseRepository.selectPurchaseDv403(customerId,start, start+2);
	}

}
