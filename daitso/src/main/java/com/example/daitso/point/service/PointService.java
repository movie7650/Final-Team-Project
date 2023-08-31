package com.example.daitso.point.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.point.model.Point;
import com.example.daitso.point.repository.IPointRepository;

@Service
public class PointService implements IPointService {
	@Autowired
	IPointRepository pointRepository;

	@Override
	public void insertPoint(Point point) {
		pointRepository.insertPoint(point);
		
	}

	@Override
	public void updatePoint(Point point) {
		pointRepository.updatePoint(point);
		
	}

	@Override
	public List<Point> selectPoint() {
		// TODO Auto-generated method stub
		return pointRepository.selectPoint();
	}



	



}
