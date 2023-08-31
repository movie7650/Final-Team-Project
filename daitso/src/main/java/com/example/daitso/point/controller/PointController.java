package com.example.daitso.point.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.daitso.point.model.Point;

import com.example.daitso.point.service.IPointService;

@Controller
@RequestMapping("/point")
public class PointController {
	@Autowired
	IPointService pointService;
	
	@RequestMapping(value="/mypoint", method=RequestMethod.GET)
	public String selectPoint(Model model) {
		List<Point> points = pointService.selectPoint();
		model.addAttribute("points",points);
		int totalPoint = 0;
		for(Point point : points) {
			totalPoint += point.getPointAfter();
		}
		model.addAttribute("totalPoint", totalPoint + "P");
		return "mypage/my-point";
	}

}
