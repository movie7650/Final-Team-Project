package com.example.daitso.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
