package com.example.daitso.point.service;

import java.util.List;

import com.example.daitso.point.model.Point;
import com.example.daitso.point.model.TotalPoint;

public interface IPointService {
	void insertPoint(Point point);
	void updatePoint(Point point);
	List<Point> selectPoint();
	int selectTotalPoint();
}
