package com.example.daitso.point.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.point.model.Point;

@Mapper
@Repository
public interface IPointRepository {
	void insertPoint(Point point);
	void updatePoint(Point point);
	void selectPoint(int pointId);
	
}
