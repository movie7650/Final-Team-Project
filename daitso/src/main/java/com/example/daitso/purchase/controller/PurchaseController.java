package com.example.daitso.purchase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.purchase.model.Purchase;
import com.example.daitso.purchase.model.PurchaseCheck;
import com.example.daitso.purchase.service.IPurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	@Autowired
	IPurchaseService purchaseService;
	
	@RequestMapping(value="/orderlist", method=RequestMethod.GET)
	public String selectPurchase(Purchase purchase, Model model, PurchaseCheck purchasecheck) {
		List<PurchaseCheck> purchases = purchaseService.selectAllPurchase();
		model.addAttribute("purchases", purchases.get(0));
		return "mypage/order-list";
	}

}
