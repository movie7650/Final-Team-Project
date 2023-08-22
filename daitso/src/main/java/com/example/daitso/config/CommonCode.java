package com.example.daitso.config;

import java.sql.Date;

import lombok.Data;

@Data
public class CommonCode {
	private int commonCodeId;
	private String commonCodeNm;
	private int commonCodePr;
	private int sortOrder;
	private char status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
}
