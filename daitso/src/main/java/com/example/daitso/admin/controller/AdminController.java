package com.example.daitso.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	// 로그인
	@GetMapping("/login")
	public String login() {
		return "admin/login/admin-login";
	}
}
