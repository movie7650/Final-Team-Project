package com.example.daitso.point.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.daitso.point.model.Point;
import com.example.daitso.point.model.TotalPoint;

@Mapper
@Repository
public interface IPointRepository {
	void insertPoint(Point point);
	void updatePoint(Point point);
	List<Point> selectPoint();
	int selectTotalPoint();
	
}
