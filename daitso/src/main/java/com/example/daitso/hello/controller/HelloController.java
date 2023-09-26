package com.example.daitso.hello.controller;

import java.util.List;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.config.InfiniteStreamRecognize;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.model.SpecialProduct;
import com.example.daitso.product.service.IProductService;

@Controller
@RequestMapping("/")
public class HelloController {
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ICategoryService categoryService;
	
	//메인 페이지
	@GetMapping("")
	public String hello(Model model) {
		List<SpecialProduct> list = productService.selectSpecialProduct();
		List<Product> sList = productService.saleProductList();
		List<Category> cList = categoryService.selectMainCategory();
		model.addAttribute("productList", list);
		model.addAttribute("saleProductList", sList);
		model.addAttribute("categoryList", cList);

		return "/main/main";
	}
	
	@GetMapping("voice")
	@ResponseBody
	public String stt(Model model) {
		System.out.println("fuckyou");
		InfiniteStreamRecognize.doThat(null);
		return "dd";
	}
	
	/*
	 * @GetMapping("voice") public void stt(Model model) { String clientId =
	 * "gyx08gz74r"; // Application Client ID"; String clientSecret =
	 * "mE1jyLKddoRWNXQPS6QgHVeaiwmFukUTDIe2VXI0"; // Application Client Secret";
	 * 
	 * try { String imgFile = "음성 파일 경로"; File voiceFile = new File(imgFile);
	 * 
	 * String language = "Kor"; // 언어 코드 ( Kor, Jpn, Eng, Chn ) String apiURL =
	 * "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language; URL
	 * url = new URL(apiURL);
	 * 
	 * HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	 * conn.setUseCaches(false); conn.setDoOutput(true); conn.setDoInput(true);
	 * conn.setRequestProperty("Content-Type", "application/octet-stream");
	 * conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
	 * conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
	 * 
	 * OutputStream outputStream = conn.getOutputStream(); FileInputStream
	 * inputStream = new FileInputStream(voiceFile); byte[] buffer = new byte[4096];
	 * int bytesRead = -1; while ((bytesRead = inputStream.read(buffer)) != -1) {
	 * outputStream.write(buffer, 0, bytesRead); } outputStream.flush();
	 * inputStream.close(); BufferedReader br = null; int responseCode =
	 * conn.getResponseCode(); if(responseCode == 200) { // 정상 호출 br = new
	 * BufferedReader(new InputStreamReader(conn.getInputStream())); } else { // 오류
	 * 발생 System.out.println("error!!!!!!! responseCode= " + responseCode); br = new
	 * BufferedReader(new InputStreamReader(conn.getInputStream())); } String
	 * inputLine;
	 * 
	 * if(br != null) { StringBuffer response = new StringBuffer(); while
	 * ((inputLine = br.readLine()) != null) { response.append(inputLine); }
	 * br.close(); System.out.println(response.toString()); } else {
	 * System.out.println("error !!!"); } } catch (Exception e) {
	 * System.out.println(e); }
	 * 
	 * }
	 */
}
