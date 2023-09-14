package com.example.daitso.purchase.controller;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.daitso.cart.controller.CartController;
import com.example.daitso.cart.model.CartPurchase;
import com.example.daitso.cart.model.Tomorrow;
import com.example.daitso.cart.service.ICartService;
import com.example.daitso.customer.model.CustomerInfo;
import com.example.daitso.customer.service.ICustomerService;
import com.example.daitso.shipping.model.ShippingInfo;
import com.example.daitso.shipping.service.IShippingService;


@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
//	@Autowired
//	IPurchaseService purchaseService;
//	
//	@RequestMapping(value="/orderlist", method=RequestMethod.GET)
//	public String selectPurchase(Purchase purchase, Model model, PurchaseCheck purchasecheck) {
//		List<PurchaseCheck> purchases = purchaseService.selectAllPurchase();
//		model.addAttribute("purchases", purchases.get(0));
//		return "mypage/order-list";
//	}
	
	@Autowired
	CartController cartController;
	
	@Autowired
	ICartService cartService;
	
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	IShippingService shippingService;
	
	// 구매 화면
	@GetMapping("")
	public String getPurchase(Model model, RedirectAttributes redirectAttributes) {
		try {
			// spring security -> 사용자 고유번호 받아오기
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails) principal;
			
			int customerId = Integer.valueOf(userDetails.getUsername());
			model.addAttribute("customerId", customerId);
			
			List<CartPurchase> cartProductsBeforePurchase = cartService.getCartProductBeforePurchaseByCustomerId(customerId);
			model.addAttribute("cartProductsBeforePurchase", cartProductsBeforePurchase);
			
			Tomorrow tommorrow = cartController.getTomorrowMonthAndDay();
			model.addAttribute("tommorrow", tommorrow);
			
			CustomerInfo customerInfo = customerService.getCustomerInfoByCustomerId(customerId);
			model.addAttribute("customerInfo", customerInfo);
			
			if(customerInfo.getShippingId() != 0) {
				ShippingInfo shipping = shippingService.getShippingInfoByShippingId(customerInfo.getShippingId());
				model.addAttribute("shipping", shipping);
			} else if(customerInfo.getRecentShippingId() != 0) {
				ShippingInfo recentShipping = shippingService.getShippingInfoByShippingId(customerInfo.getRecentShippingId());
				model.addAttribute("recentShipping", recentShipping);
			}
			
			return "purchase/purchase";
		} catch (ClassCastException e) {
			redirectAttributes.addFlashAttribute("error", "다시 로그인 해주세요!");
			return "redirect:/customer/login";
		}
	}

}
