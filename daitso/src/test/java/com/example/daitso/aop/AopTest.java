package com.example.daitso.aop;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.example.daitso.cart.repository.ICartRepository;
import com.example.daitso.cart.service.ICartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Import(Aspect.class)
public class AopTest {
	
	@Autowired
	ICartService cartService;
	
	@Autowired
	ICartRepository cartRepository;
	
	@Test
	void aopInfo() {
		log.info("isAopProxy, cartService={}", AopUtils.isAopProxy(cartService));
		log.info("isAopProxy, cartRepository={}", AopUtils.isAopProxy(cartRepository));
	}
	
	@Test
	void success() {
		cartService.getCartByCustomerId(27);
	}
}
