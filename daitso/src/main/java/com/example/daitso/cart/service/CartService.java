package com.example.daitso.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartUpdate;
import com.example.daitso.cart.repository.ICartRepository;

@Service
public class CartService implements ICartService{
	@Autowired
	ICartRepository cartRepository;

	// 장바구니 추가
	@Override
	public void insertCart(int productId, int customerId, int productCnt) {
		cartRepository.insertCart(productId, customerId, productCnt);
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
	public void deleteCartByCartId(int cartId) {
		cartRepository.deleteCartByCartId(cartId);
	}
}
