package com.example.daitso.point.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Point {
	private int pointId;
	private int customerId;
	private int pointBefore;
	private int pointAfter;
	private String pointUpdateContent;
	private int pointUseDv;
	private String status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
}
