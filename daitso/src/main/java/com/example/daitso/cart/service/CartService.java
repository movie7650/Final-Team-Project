package com.example.daitso.cart.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartCoupon;
import com.example.daitso.cart.model.CartPurchase;
import com.example.daitso.cart.model.CartUpdate;
import com.example.daitso.cart.repository.ICartRepository;

@Service
public class CartService implements ICartService{
	
	@Autowired
	ICartRepository cartRepository;

	// 장바구니 추가
	@Transactional
	public void insertCartService(int productId, int customerId, int productCnt) {
		Map<String, Integer> map =  cartRepository.selectCustomerCartProduct(customerId, productId);
		if(map == null) {
			cartRepository.insertCart(productId, customerId, productCnt);			
		}else {
			int maxVal = Integer.parseInt(String.valueOf(map.get("PRODUCTMAXGET")));
			int productCartVal = Integer.parseInt(String.valueOf(map.get("CARTCOUNT")));
			if(productCnt + productCartVal <= maxVal) {
				cartRepository.updateCartCnt(productId, customerId, productCnt + productCartVal);
			}
			else {
				cartRepository.updateCartCnt(productId, customerId, maxVal);
				
			}
		}
	}
	
	// 사용자 고유번호로부터 사용자 장바구니 조회
	@Override
	public List<CartCheck> getCartByCustomerId(int customerId) {
		return cartRepository.getCartByCustomerId(customerId);
	}

	// 사용자 고유번호로부터 사용자 장바구니에 담긴 물건 개수 조회
	@Override
	public int getCartCountByCustomerId(int customerId) {
		return cartRepository.getCartCountByCustomerId(customerId);
	}

	// 장바구니 수정(개수,총가격)
	@Transactional
	public void updateCartCountByCartId(CartUpdate cartUpdate) {
		cartRepository.updateCartCountByCartId(cartUpdate);
	}

	// 장바구니 삭제
	@Transactional
	public void deleteCartByCartId(List<Integer> cartIdList, int customerId) {
		cartRepository.deleteCartByCartId(cartIdList, customerId);
	}

	// 장바구니 체크박스 상태 변경
	@Override
	public void updateCheckedByCartId(List<Integer> cartIdList, int customerId, String checked) {
		cartRepository.updateCheckedByCartId(cartIdList, customerId, checked);
		
	}

	// 쿠폰 적용 대상 상품들 조회
	@Override
	public List<CartCoupon> getCouponProductByCustomerId(int customerId) {
		return cartRepository.getCouponProductByCustomerId(customerId);
	}

	// 구매하기 전 장바구니에 담긴 물건들 조회 
	@Override
	public List<CartPurchase> getCartProductBeforePurchaseByCustomerId(int customerId) {
		return cartRepository.getCartProductBeforePurchaseByCustomerId(customerId);
	}

	@Override
	public void directPurchase(int productId, int customerId, int productCnt) {
		cartRepository.directPurchase(productId, customerId, productCnt);
	}

	// 바로 구매시 해당 상품 정보들 장바구니 화면에 띄우기
	@Override
	public List<CartCheck> getCartByCartIdDirectPurchase() {
		return cartRepository.getCartByCartIdDirectPurchase();
	}

	// 쿠폰 적용 대상 상품들 조회(바로구매)
	@Override
	public List<CartCoupon> getCouponProductByCustomerIdAndCartId(int customerId, int cartId) {
		return cartRepository.getCouponProductByCustomerIdAndCartId(customerId, cartId);
	}

	// 구매하기 전 장바구니에 담긴 물건들 조회(바로구매) 
	@Override
	public List<CartPurchase> getCartProductBeforePurchaseByCustomerIdAndCartId(int customerId, int cartId) {
		return cartRepository.getCartProductBeforePurchaseByCustomerIdAndCartId(customerId, cartId);
	}
}
