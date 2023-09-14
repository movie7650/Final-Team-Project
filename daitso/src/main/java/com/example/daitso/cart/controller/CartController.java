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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.daitso.cart.model.CartCheck;
import com.example.daitso.cart.model.CartCoupon;
import com.example.daitso.cart.model.CartUpdate;
import com.example.daitso.cart.model.Tomorrow;
import com.example.daitso.cart.service.ICartService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	// 장바구니 조회
	@GetMapping("")
	public String getCart(Model model, RedirectAttributes redirectAttributes) {
		try {
			// spring security -> 사용자 고유번호 받아오기
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails) principal;
			
			int customerId = Integer.valueOf(userDetails.getUsername());
			model.addAttribute("customerId",customerId);

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
		} catch (ClassCastException e) {
			redirectAttributes.addFlashAttribute("error", "다시 로그인 해주세요!");
			return "redirect:/customer/login";
		}
	}
	
	// 장바구니 수정(개수,총가격)
	@PatchMapping("/update")
	public @ResponseBody String updateCart(@RequestBody String data) {
		
		JsonElement element = JsonParser.parseString(data);
		String cartId = element.getAsJsonObject().get("cartId").getAsString();
		String cartCount = element.getAsJsonObject().get("cartCount").getAsString();
		String cartPrice = element.getAsJsonObject().get("cartPrice").getAsString();
		String customerId = element.getAsJsonObject().get("customerId").getAsString();
		
		CartUpdate cartUpdate = CartUpdate.builder()
								.cartId(Integer.valueOf(cartId))
								.cartCount(Integer.valueOf(cartCount))
								.cartPrice(cartPrice)
								.customerId(Integer.valueOf(customerId))
								.build();
		cartService.updateCartCountByCartId(cartUpdate);
		return cartId;
	}
	
	// 장바구니 선택상품 삭제 및 품절,판매중지 상품 삭제
	@PatchMapping("/delete/{customerId}")
	public @ResponseBody String deleteCart(@PathVariable int customerId, @RequestBody String data) {
		JsonElement element = JsonParser.parseString(data);
		JsonArray jsonArray = element.getAsJsonObject().getAsJsonArray("itemList");
		List<Integer> cartIdList = new Gson().fromJson(jsonArray, new TypeToken<List<Integer>>(){}.getType());
		
		cartService.deleteCartByCartId(cartIdList, customerId);
		return data;
	}
	
	// 장바구니 체크박스 상태 변경
	@PatchMapping("/update/checkbox/{customerId}")
	public @ResponseBody String updateCartChecked(@PathVariable int customerId, @RequestBody String data) {
		JsonElement element = JsonParser.parseString(data);
		String checked = element.getAsJsonObject().get("checked").getAsString();
		JsonArray jsonArray = element.getAsJsonObject().getAsJsonArray("cartIdList");
		List<Integer> cartIdList = new Gson().fromJson(jsonArray, new TypeToken<List<Integer>>(){}.getType());
	
		cartService.updateCheckedByCartId(cartIdList, customerId, checked);
		return data;
	}
	
	// 내일 날짜 구하기
	public Tomorrow getTomorrowMonthAndDay() {

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
		
		// 주말에는 배달을 안한다는 가정하...
		if("(토)".equals(dof)) {
			cal.add(cal.DATE, +2);
			String mdate = sdf.format(cal.getTime());
			String[] mdateSplit = mdate.split("/");
			String mmad = mdateSplit[1] + "/" + mdateSplit[2];

			// 담주 월요일 날짜(요일)
			LocalDate nm = LocalDate.of(Integer.valueOf(mdateSplit[0]), Integer.valueOf(mdateSplit[1]),
					Integer.valueOf(mdateSplit[2]));

			DayOfWeek mdayOfWeek = nm.getDayOfWeek();
			String mdof = "(" + mdayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN) + ")";
			
			return Tomorrow.builder().mad(mmad).dof(mdof).build();
		} else if("(일)".equals(dof)){
			cal.add(cal.DATE, +1);
			String mdate = sdf.format(cal.getTime());
			String[] mdateSplit = mdate.split("/");
			String mmad = mdateSplit[1] + "/" + mdateSplit[2];

			// 담주 월요일 날짜(요일)
			LocalDate nm = LocalDate.of(Integer.valueOf(mdateSplit[0]), Integer.valueOf(mdateSplit[1]),
					Integer.valueOf(mdateSplit[2]));

			DayOfWeek mdayOfWeek = nm.getDayOfWeek();
			String mdof = "(" + mdayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN) + ")";
			
			return Tomorrow.builder().mad(mmad).dof(mdof).build();
		} else {
			return Tomorrow.builder().mad(mad).dof(dof).build();
		}
	}
	
	//장바구니 추가
	@PostMapping("/insert")
	public String insertCart(int productId, int productCnt, int totalPrice) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;

		int customerId = Integer.parseInt(userDetails.getUsername());
		
		cartService.insertCart(productId, customerId, productCnt, totalPrice);
		
		return "redirect:/cart";
	}
	
	// 장바구니 쿠폰 적용화면
	@GetMapping("/coupon/{customerId}")
	public String getCartCoupon(@PathVariable int customerId, Model model) {
		List<CartCoupon> cartCouponProducts = cartService.getCouponProductByCustomerId(customerId);
		model.addAttribute("customerId", customerId);
		model.addAttribute("cartCouponProducts", cartCouponProducts);
		return "cart/cart-coupon";
	}
}
