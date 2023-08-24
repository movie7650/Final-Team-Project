package com.example.daitso;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class FrontEndTest {
	
	
	@RequestMapping("/")
	public String hello() {
		
		return "test";
	}
}
