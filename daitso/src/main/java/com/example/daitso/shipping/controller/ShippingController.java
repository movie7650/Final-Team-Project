package com.example.daitso.shipping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.shipping.model.ShippingAdd;
import com.example.daitso.shipping.model.ShippingInfo;
import com.example.daitso.shipping.service.IShippingService;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
	
	@Autowired
	IShippingService shippingService;
	
	// 배송지 조회 화면
	@GetMapping("/{customerId}")
	public String getShipping(Model model, @PathVariable int customerId) {
		List<ShippingInfo> shippings = shippingService.getShippingInfoByCustomerId(customerId);
		model.addAttribute("shippings",shippings);
		model.addAttribute("customerId",customerId);
		return "shipping/shipping";
	}
	
	// 배송지 추가 화면
	@GetMapping("/{customerId}/add")
	public String addShipping(Model model,@PathVariable int customerId, ShippingAdd shippingAdd) {
		return "shipping/add-shipping";
	}
	
	// 배송지 추가
	@PostMapping("/{customerId}/add")
	public String addShipping(@PathVariable int customerId, ShippingAdd shippingAdd) {
		if(shippingAdd.getShippingDv() == null) {
			shippingAdd.setShippingDv("302");
		} 
		
		if(shippingAdd.getShippingDmnd().isEmpty()) {
			shippingAdd.setShippingDmnd("문 앞");
		}
		
		shippingService.addShipping(shippingAdd, customerId);
		return "redirect:/shipping/" + customerId;
	}
	
	// 배송지 수정 화면
	@GetMapping("/{customerId}/update/{shippingId}")
	public String updateShipping(Model model, @PathVariable int customerId, @PathVariable int shippingId, ShippingAdd shippingUpdate) {
		ShippingInfo shipping = shippingService.getShippingInfoByShippingId(shippingId);
		model.addAttribute("shipping",shipping);
		return "shipping/update-shipping";
	}
	
	// 배송지 수정
	@PostMapping("/{customerId}/update/{shippingId}")
	public String updateShipping(@PathVariable int customerId, @PathVariable int shippingId, ShippingAdd shippingUpdate) {
		if(shippingUpdate.getShippingDv() == null) {
			shippingUpdate.setShippingDv("302");
		} 
		shippingService.updateShipping(shippingUpdate, shippingId, customerId);
		return "redirect:/shipping/" + customerId;
		
	}
	
	// 배송지 삭제
	@PostMapping("/{customerId}/delete/{shippingId}")
	public String deleteShipping(@PathVariable int shippingId, @PathVariable int customerId) {
		shippingService.deleteShipping(shippingId);
		return "redirect:/shipping/" + customerId;
	}
	
	// 배송지 조회
	@GetMapping("/api/{shippingId}")
	public @ResponseBody ShippingInfo getShipping(@PathVariable int shippingId) {
		return shippingService.getShippingInfoByShippingId(shippingId);
	}
}
