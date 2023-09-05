package com.example.daitso.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.daitso.cart.service.ICartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	
	@PostMapping("/insert")
	public String cartInsert(@RequestParam(value="productId") int productId, @RequestParam(value="productCnt") int productCnt) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		int customerId = Integer.parseInt(userDetails.getUsername());
		
		System.out.println("im here" + productId + " " + productCnt + " " + customerId);
		cartService.insertCart(productId, customerId, productCnt);
		return "redirect:/category/1";
	}
}
