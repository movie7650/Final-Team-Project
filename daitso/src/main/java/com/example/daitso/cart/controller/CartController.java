package com.example.daitso.cart.controller;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartUpdate;
import com.example.daitso.cart.model.Tomorrow;
import com.example.daitso.cart.service.ICartService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	// 장바구니 조회
	@GetMapping("")
	public String getCart(Model model) {

		// spring security -> 사용자 고유번호 받아오기
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;

		int customerId = Integer.valueOf(userDetails.getUsername());

		// 장바구니 조회
		List<CartCheck> cartList = cartService.getCartByCustomerId(customerId);
		model.addAttribute("cartList", cartList);

		// 장바구니에 담긴 총 물건 개수 조회
		int cartTotalCount = cartService.getCartCountByCustomerId(customerId);
		model.addAttribute("cartTotalCount", cartTotalCount);
		
		// 내일 날짜 구하기
		Tomorrow tomorrow = getTomorrowMonthAndDay();
		model.addAttribute("tomorrow", tomorrow);
		
		return "cart/cart";
	}
	
	// 장바구니 수정(개수,총가격)
	@PatchMapping("/update")
	public @ResponseBody String updateCart(@RequestBody String data) {
		
		JsonElement element = JsonParser.parseString(data);
		String cartId = element.getAsJsonObject().get("cartId").getAsString();
		String cartCount = element.getAsJsonObject().get("cartCount").getAsString();
		String purchaseNum = element.getAsJsonObject().get("purchaseNum").getAsString();
		
		CartUpdate cartUpdate = CartUpdate.builder()
								.cartId(Integer.valueOf(cartId))
								.cartCount(Integer.valueOf(cartCount))
								.purchaseNum(purchaseNum).build();
		cartService.updateCartCountByCartId(cartUpdate);
		return cartId;
	}
	
	// 장바구니 선택상품 삭제
	@PatchMapping("/delete/{cartId}")
	public @ResponseBody String deleteCart(@PathVariable int cartId) {
		cartService.deleteCartByCartId(cartId);
		return String.valueOf(cartId);
	}
	
	// 내일 날짜 구하기
	private Tomorrow getTomorrowMonthAndDay() {

		// 내일 날짜(년,월,일)
		Calendar cal = Calendar.getInstance();
		String format = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		cal.add(cal.DATE, +1);
		String date = sdf.format(cal.getTime());
		String[] dateSplit = date.split("/");
		String mad = dateSplit[1] + "/" + dateSplit[2];

		// 내일 날짜(요일)
		LocalDate tomorrow = LocalDate.of(Integer.valueOf(dateSplit[0]), Integer.valueOf(dateSplit[1]),
				Integer.valueOf(dateSplit[2]));

		DayOfWeek dayOfWeek = tomorrow.getDayOfWeek();
		String dof = "(" + dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN) + ")";

		return Tomorrow.builder().mad(mad).dof(dof).build();
	}
}
