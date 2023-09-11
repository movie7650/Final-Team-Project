package com.example.daitso.cart.service;

import java.util.List;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartCoupon;
import com.example.daitso.cart.model.CartCouponApply;
import com.example.daitso.cart.model.CartUpdate;

public interface ICartService {
	
	// 장바구니 추가
	void insertCart(int productId, int customerId, int productCnt, int totalPrice);
	
	// 사용자 고유번호로부터 사용자 장바구니 조회
	List<CartCheck> getCartByCustomerId(int customerId);
	
	// 사용자 고유번호로부터 사용자 장바구니에 담긴 물건 개수 조회
	int getCartCountByCustomerId(int customerId);
	
	// 장바구니 수정(개수,총가격)
	void updateCartCountByCartId(CartUpdate cartUpdate);
	
	// 장바구니 삭제
	void deleteCartByCartId(List<Integer> cartIdList, int customerId);
	
	// 장바구니 체크박스 상태 변경
	void updateCheckedByCartId(List<Integer> cartIdList, int customerId, String checked);
	
	// 쿠폰 적용 대상 상품들 조회
    List<CartCoupon> getCouponProductByCustomerId(int customerId);
    
    // 적용가능한 쿠폰 조회
 	List<CartCouponApply> getCouponsByCustomerId(List<Integer> categoryIdList, int customerId);
}
