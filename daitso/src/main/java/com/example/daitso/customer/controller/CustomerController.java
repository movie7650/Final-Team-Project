package com.example.daitso.customer.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.example.daitso.check.ILogincheckService;
import com.example.daitso.customer.model.CustomerEmail;
import com.example.daitso.customer.model.CustomerLogin;
import com.example.daitso.customer.model.CustomerName;
import com.example.daitso.customer.model.CustomerSignUp;
import com.example.daitso.customer.service.ICustomerService;
import com.example.daitso.oauth.model.OAuth2UserDetails;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;



@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	ILogincheckService logincheckService;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	final DefaultMessageService messageService;
	
	public CustomerController() {
		this.messageService = NurigoApp.INSTANCE.initialize("NCSMUQ7XSD3KHBVP", "NZP65I5POQTIFH4RIMSRE15IK0ZXQCLP", "https://api.coolsms.co.kr");
	}
	
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
	
	// 이메일 중복체크
	@GetMapping("/email-dupllicated/{customerEmail}")
	public @ResponseBody Optional<Integer> checkEmailDuplicated(@PathVariable String customerEmail) {
		return customerService.checkEmailDuplicated(customerEmail);
	}
	
	// 휴대폰번호 중복체크
	@GetMapping("/telno-dupllicated/{customerTelno}")
	public @ResponseBody Optional<Integer> checkTelnoDuplicated(@PathVariable String customerTelno) {
		return customerService.checkTelnoDuplicated(customerTelno);
	}
	
	// 이메일 인증
	@GetMapping("/auth/{customerEmail}")
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
			createNum = random.nextInt(9) + 1;
			ranNum = Integer.toString(createNum);
			resultNum += ranNum;
		}
		return resultNum;
	}
	
	// 스프링 시큐리티에서 사용자 정보(사용자 고유번호) 받아오기
	@GetMapping("/customer-id")
	public @ResponseBody String getCustomerId(){
		
		// spring security -> 사용자 고유번호 받아오기
		String customerId = String.valueOf(logincheckService.loginCheck());
		
		if("-1".equals(customerId)) {
			return "-1";
		}
		
		return customerId;
	}
	
	// 사용자 고유번호로부터 사용자 이름 갖고오기
	@GetMapping("/customer-nm/{customerId}")
	public @ResponseBody CustomerName getCustomerNmByCustomerId(@PathVariable int customerId) {
		return customerService.getCustomerNmByCustomerId(customerId);
	}
	
	// 아이디 찾기 화면
	@GetMapping("/find/email")
	public String getCustomerFindEmail() {
		return "customer/customer-find-email";
	}
	
	// 비밀번호 찾기 화면
	@GetMapping("/find/pw")
	public String getCustomerFindPw() {
		return "customer/customer-find-pw";
	}
	
	// 사용자에게 비밀번호 세팅창 메일로 보내기
	@PostMapping("/setting-password/email")
	public @ResponseBody String settingPassword(@RequestBody String data, HttpServletRequest request) {
		
		JsonElement element = JsonParser.parseString(data);
		String email = element.getAsJsonObject().get("email").getAsString();
		
		String urlString = request.getRequestURL().toString();
		String url = urlString.substring(0, urlString.length() - 6) + "/" + email;
		
		// 이메일 보내기    
        try {
        	MimeMessage m = javaMailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(m,"UTF-8");
			
            h.setFrom("cjw9977@naver.com");
			h.setTo(email);
	        h.setSubject("[쿠팡] 비밀번호 재설정을 위한 안내메일입니다.");
	        h.setText("<html><body> "
	        		+ "<div>비밀번호 재설정을 위한 안내 이메일입니다!. 비밀번호 설정할려면 아래 링크로 가주세요!<div>"
	        		+ "<div><a href=" + url + ">비밀번호 설정하기</a></div>"
	        		+ "</body></html>", true);
	        javaMailSender.send(m);
	        
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	// 새로운 비밀번호 설정 화면
	@GetMapping("/setting-password/{customerEmail}")
	public String settingPassword(@PathVariable String customerEmail, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("email", customerEmail);
		return "redirect:/customer/setting-password";
	}
	
	@GetMapping("/setting-password")
	public String settingPassword() {
		return "customer/setting-password";
	}
	
	// 새로운 비밀번호 설정
	@PostMapping("/setting-password")
	public String settingPassword(@RequestParam String password, @RequestParam String email, RedirectAttributes redirectAttributes) {
		customerService.settingPassword(password,email);
		if(email == "") {
			redirectAttributes.addFlashAttribute("error", "다시 전송해주세요.");
			return "redirect:/customer/find/pw";
		}
		
		redirectAttributes.addFlashAttribute("error", "비밀번호가 새로 설정되었습니다.");
		return "redirect:/customer/login";
	}
	
	// 휴대폰번호로 인증번호 보내기
	@PostMapping("/send-message")
	public @ResponseBody String sendMessage(@RequestBody String data) {
		JsonElement element = JsonParser.parseString(data);
		String telno = element.getAsJsonObject().get("telno").getAsString();
		
	    String randomNumber = randomNumber();
	    	
		Message message = new Message(); message.setFrom("01087458992");
		message.setTo(telno); message.setText("[다있소] 인증번호는" + "["+randomNumber+"]" + "입니다.");
		  
	    this.messageService.sendOne(new SingleMessageSendingRequest(message));
		
		return randomNumber;
	}
	
	// 이메일 조회 
	@GetMapping("/email/{customerTelno}")
	public @ResponseBody CustomerEmail getCustomerEmail(@PathVariable String customerTelno) {
		String email = customerService.getCustomerEmailByCustomerTelno(customerTelno);
		CustomerEmail customerEmail = CustomerEmail.builder().customerEmail(email).build();
		
		return customerEmail;
	}
}
