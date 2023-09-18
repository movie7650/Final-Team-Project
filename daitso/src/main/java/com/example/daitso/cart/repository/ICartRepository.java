package com.example.daitso.cart.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartCoupon;
import com.example.daitso.cart.model.CartPurchase;
import com.example.daitso.cart.model.CartUpdate;

@Repository
@Mapper
public interface ICartRepository {
	
	// 장바구니에 담겨있지 않은 상품 담기
	void insertCart(int productId, int customerId, int productCnt);
	
	// 기능1: 장바구니에 담겨있는 상품 담기(개수만 추가)
	// 기능2: 바로 구매하기 전 장바구니 업데이트
	void updateCartCnt(int productId, int customerId, int productCnt);

	// 사용자 고유번호로부터 사용자 장바구니 조회
	List<CartCheck> getCartByCustomerId(int customerId);
	
	// 사용자 고유번호로부터 사용자 장바구니에 담긴 물건 개수 조회
	int getCartCountByCustomerId(int customerId);
	
	// 장바구니 수정(개수,총가격)
	void updateCartCountByCartId(CartUpdate cartUpdate);
	
	// 장바구니 삭제
	void deleteCartByCartId(@Param("cartIdList") List<Integer> cartIdList, @Param("customerId") int customerId);
	
	// 장바구니 체크박스 상태 변경
	void updateCheckedByCartId(@Param("cartIdList") List<Integer> cartIdList, @Param("customerId") int customerId, @Param("checked") String checked);
	
	// 쿠폰 적용 대상 상품들 조회
	List<CartCoupon> getCouponProductByCustomerId(int customerId);
	
	// 구매하기 전 장바구니에 담긴 물건들 조회 
	List<CartPurchase> getCartProductBeforePurchaseByCustomerId(int customerId);
	
	// 장바구니 추가하기 전 기존에 추가된 상품 있는지 조회
	Map<String, Integer> selectCustomerCartProduct(int customerId, int productId);
	
	// customer_coupon_id 업데이트
	void updateCustomerCouponId(@Param("cartId") int cartId, @Param("customerCouponId") int customerCouponId);

}
