package com.example.daitso.point.service;

import com.example.daitso.point.model.Point;

public interface IPointService {
	void insertPoint(Point point);
	void updatePoint(Point point);
	void selectPoint(int pointId);

}
