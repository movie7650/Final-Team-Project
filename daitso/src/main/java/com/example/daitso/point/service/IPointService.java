package com.example.daitso.point.service;

import java.util.List;

import com.example.daitso.point.model.Point;

public interface IPointService {
	void insertPoint(Point point);
	void updatePoint(Point point);
	List<Point> selectPoint();

}
