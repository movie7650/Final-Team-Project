package com.example.daitso.translate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.translate.service.TranslateService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/translate")
public class TranslateController {

	@Autowired
	TranslateService translateService;
	
	@PostMapping("")
	public @ResponseBody String translateText(@RequestBody String data) {
		JsonElement element = JsonParser.parseString(data);
		String text = element.getAsJsonObject().get("text").getAsString();
		
		return translateService.getTransSentence(text);
	}
}
