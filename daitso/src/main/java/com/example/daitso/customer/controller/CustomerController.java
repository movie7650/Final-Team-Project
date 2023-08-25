package com.example.daitso.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daitso.customer.model.CustomerLogin;
import com.example.daitso.customer.model.CustomerSignUp;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@GetMapping("/sign-up")
	public String signUpGet(CustomerSignUp customerSignUp) {
		return "sign-up/sign-up";
	}
	
	@PostMapping("/sign-up")
	public String signUpPost(CustomerSignUp customerSignUp) {
		return "";
	}
	
	@GetMapping("/login")
	public String loginGet(CustomerLogin customerLogin) {
		return "login/login";
	}
	
	@PostMapping("/login")
	public String loginPost(CustomerLogin customerLogin) {
		return "login/login";
	}
	
}
