package com.example.daitso.purchase.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.daitso.cart.controller.CartController;
import com.example.daitso.cart.model.CartPurchase;
import com.example.daitso.cart.model.Tomorrow;
import com.example.daitso.cart.service.ICartService;
import com.example.daitso.customer.model.CustomerInfo;
import com.example.daitso.customer.service.ICustomerService;
import com.example.daitso.purchase.model.PurchaseInsert;
import com.example.daitso.purchase.model.PurchaseNum;
import com.example.daitso.purchase.model.PurchaseSuccess;
import com.example.daitso.purchase.service.IPurchaseService;
import com.example.daitso.shipping.model.ShippingInfo;
import com.example.daitso.shipping.service.IShippingService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpServletRequest;



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
	
	@Autowired
	IPurchaseService purchaseService;
	
	/*
	 *  구매 화면 
	 *  cartId -> 0이면 장바구니에서 구매, 0이 아니면 바로 구매
	 */
	@GetMapping("/{cartId}")
	public String getPurchase(@PathVariable int cartId, Model model, RedirectAttributes redirectAttributes) {
		try {
			// spring security -> 사용자 고유번호 받아오기
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails) principal;
			
			int customerId = Integer.valueOf(userDetails.getUsername());
			model.addAttribute("customerId", customerId);
			
			if(cartId == 0) {
				List<CartPurchase> cartProductsBeforePurchase = cartService.getCartProductBeforePurchaseByCustomerId(customerId);
				model.addAttribute("cartProductsBeforePurchase", cartProductsBeforePurchase);
			} else {
				List<CartPurchase> cartProductsBeforePurchase = cartService.getCartProductBeforePurchaseByCustomerIdAndCartId(customerId, cartId);
				model.addAttribute("cartProductsBeforePurchase", cartProductsBeforePurchase);
			}
			
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
	
	// 주문번호 생성
	@GetMapping("/purchase-num")
	public @ResponseBody PurchaseNum getPurchaseNum() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddhhmmssSSSSSSSSS");
        String purchaseNum = sdf.format(timestamp);
        return PurchaseNum.builder().purchaseNum(purchaseNum).build(); 
	}
	
	// 구매 성공시 나오는 화면
	@GetMapping("/success/complete")
	public String getPurchaseSuccess(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, 
			@RequestParam int shippingId, @RequestParam List<Integer> cartIdList,
			@RequestParam String totalProductPrice, @RequestParam String discountPrice) {
		try {
			
			// spring security -> 사용자 고유번호 받아오기
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails) principal;
			
			int customerId = Integer.valueOf(userDetails.getUsername());
			
			String purchaseNum = request.getParameter("orderId");
			String totalCost = request.getParameter("amount");
			
			ShippingInfo shippingInfo = shippingService.getShippingInfoByShippingId(shippingId);
			
			
			PurchaseSuccess purchaseSuccess = PurchaseSuccess.builder()
															.customerId(customerId)
															.shippingId(shippingId)
															.shippingReceiverNm(shippingInfo.getShippingReceiverNm())
															.shippingReceiverTelno(shippingInfo.getShippingReceiverTelno())
															.shippingRoadNmAddr(shippingInfo.getShippingRoadNmAddr())
															.shippingDaddr(shippingInfo.getShippingDaddr())
															.shippingDmnd(shippingInfo.getShippingDmnd())
															.purchaseNum(purchaseNum)
															.totalCost(totalCost.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","))
															.totalProductPrice(totalProductPrice)
															.discountPrice(discountPrice)
															.build();
			redirectAttributes.addFlashAttribute("purchaseSuccess", purchaseSuccess);
			redirectAttributes.addFlashAttribute("cartIdList", cartIdList);
			
			return "redirect:/purchase/success";
		} catch (ClassCastException e) {
			redirectAttributes.addFlashAttribute("error", "다시 로그인 해주세요!");
			return "redirect:/customer/login";
		}
	}
	
	@GetMapping("/success")
	public String getPurchaseSuccess() {
		return "purchase/purchase-success";
	}
	
	
	// 구매 
	@PostMapping("/insert")
	public @ResponseBody String insertPurchase(@RequestBody String data){
		JsonElement element = JsonParser.parseString(data);
		
		int cartId = Integer.valueOf(element.getAsJsonObject().get("cartId").getAsString());
		int	customerCouponId = Integer.valueOf(element.getAsJsonObject().get("customerCouponId").getAsString());
		int customerId = Integer.valueOf(element.getAsJsonObject().get("customerId").getAsString());
		int shippingId = Integer.valueOf(element.getAsJsonObject().get("shippingId").getAsString());
		String purchaseNum = element.getAsJsonObject().get("purchaseNum").getAsString();
		int totalCost = Integer.valueOf(element.getAsJsonObject().get("totalCost").getAsString());
		String orderRequest = element.getAsJsonObject().get("orderRequest").getAsString();
		
		PurchaseInsert purchaseInsert = PurchaseInsert.builder()
														.cartId(cartId)
														.customerCouponId(customerCouponId)
														.customerId(customerId)
														.shippingId(shippingId)
														.purchaseNum(purchaseNum)
														.totalCost(totalCost)
														.orderRequest(orderRequest).build();
		
		purchaseService.insertPurchase(purchaseInsert); 
		return null;
	}
}
