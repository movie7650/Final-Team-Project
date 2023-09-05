package com.example.daitso.cart.service;


public interface ICartService {
	void insertCart(int productId, int customerId, int productCnt);

import java.util.List;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartUpdate;

public interface ICartService {
	
	// 사용자 고유번호로부터 사용자 장바구니 조회
	List<CartCheck> getCartByCustomerId(int customerId);
	
	// 사용자 고유번호로부터 사용자 장바구니에 담긴 물건 개수 조회
	int getCartCountByCustomerId(int customerId);
	
	// 장바구니 수정(개수,총가격)
	void updateCartCountByCartId(CartUpdate cartUpdate);
	
	// 장바구니 삭제
	void deleteCartByCartId(int cartId);
}
