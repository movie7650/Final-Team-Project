package com.example.daitso.config;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
	
	private int rn;
	private String searchText;
}
