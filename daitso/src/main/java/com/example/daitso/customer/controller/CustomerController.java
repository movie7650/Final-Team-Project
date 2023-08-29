package com.example.daitso.customer.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.daitso.customer.model.CustomerLogin;
import com.example.daitso.customer.model.CustomerSignUp;
import com.example.daitso.customer.service.ICustomerService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	ICustomerService customerService;	
	
	@Autowired
	JavaMailSender javaMailSender;
	
	// 회원가입
	@GetMapping("/sign-up")
	public String signUpGet(CustomerSignUp customerSignUp) {
		return "sign-up/sign-up";
	}
	
	@PostMapping("/sign-up")
	public String signUpPost(CustomerSignUp customerSignUp, Model model) {
		customerService.insertIntoCustomer(customerSignUp);
		return "redirect:/customer/login";
	}
	
	// 로그인
	@GetMapping("/login")
	public String loginGet(CustomerLogin customerLogin) {
		return "login/login";
	}
	
	@PostMapping("/login")
	public String loginPost(CustomerLogin customerLogin) {
		return "login/login";
	}
	
	// 이메일 중복체크
	@GetMapping("/checkEmailDupllicated/{customerEmail}")
	public @ResponseBody Optional<Integer> checkEmailDuplicated(@PathVariable String customerEmail) {
		return customerService.checkEmailDuplicated(customerEmail);
	}
	
	// 휴대폰번호 중복체크
	@GetMapping("/checkTelnoDupllicated/{customerTelno}")
	public @ResponseBody Optional<Integer> checkTelnoDuplicated(@PathVariable String customerTelno) {
		return customerService.checkTelnoDuplicated(customerTelno);
	}
	
	// 이메일 인증
	@GetMapping("/authEmail/{customerEmail}")
	public @ResponseBody String authEmail(@PathVariable String customerEmail) throws MessagingException {
		// 6자리 난수 생성
		String randomNumber = randomNumber();
		
		// 이메일 보내기 
		MimeMessage m = javaMailSender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(m,"UTF-8");
        h.setFrom("cjw9977@naver.com");
        h.setTo(customerEmail);
        h.setSubject("[다있소] 인증코드 안내");
        h.setText("인증코드는 " + randomNumber + " 입니다!");
        javaMailSender.send(m);
        
        return randomNumber;
	}

	
	// 6자리 난수 생성(인증코드)
	private String randomNumber() {
		Random random = new Random();
		int createNum = 0;
		String ranNum = "";
		int letter = 6;
		String resultNum = "";
		
		for(int i=0; i<letter; i++) {
			createNum = random.nextInt(9);
			ranNum = Integer.toString(createNum);
			resultNum += ranNum;
		}
		return resultNum;
	}
}
