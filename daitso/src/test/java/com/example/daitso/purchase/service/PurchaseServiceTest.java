package com.example.daitso.purchase.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.admin.service.IAdminService;
import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.service.ICartService;
import com.example.daitso.purchase.model.PurchaseInsert;
import com.example.daitso.purchase.model.PurchaseList;

@SpringBootTest
class PurchaseServiceTest {

	@Autowired
	IPurchaseService purchaseService;
	
	@Autowired
	IAdminService adminService;
	
	@Autowired
	ICartService cartService;
	
	@Test
	@Transactional
	void 바로구매하기() {
		// given
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddhhmmssSSSSSSSSS");
        String purchaseNum = sdf.format(timestamp);
		
		PurchaseInsert purchaseInsert = PurchaseInsert.builder()
											.cartId(109)
											.customerCouponId(0)
											.customerId(16)
											.shippingId(8)
											.purchaseNum(purchaseNum)
											.totalCost(200000)
											.orderRequest("문 앞")
											.build();
		
		// when
		purchaseService.insertPurchase(purchaseInsert);
		
		// then
		List<PurchaseList> purchaseList = adminService.getPurchaseDetails(purchaseNum);
		Assertions.assertThat(purchaseInsert.getTotalCost()).isEqualTo(purchaseList.get(0).getTotalCost());
	}
	
	@Test
	@Transactional
	void 장바구니에서구매하기() {
		// given
		List<CartCheck> cartItems = cartService.getCartByCustomerId(16);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddhhmmssSSSSSSSSS");
        String purchaseNum = sdf.format(timestamp);
		
        // when
        cartItems.forEach((cartItem) -> {
        	PurchaseInsert purchaseInsert = PurchaseInsert.builder()
															.cartId(cartItem.getCartId())
															.customerCouponId(0)
															.customerId(16)
															.shippingId(8)
															.purchaseNum(purchaseNum)
															.totalCost(200000)
															.orderRequest("문 앞")
															.build();
        	purchaseService.insertPurchase(purchaseInsert);
        });
        
		// then
		List<PurchaseList> purchaseList = adminService.getPurchaseDetails(purchaseNum);
		Assertions.assertThat(purchaseList.size()).isEqualTo(cartItems.size());	
	}

}
