package com.example.daitso.cart.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartUpdate;

@Repository
@Mapper
public interface ICartRepository {

	void insertCart(int productId, int customerId, int productCnt);

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
}
