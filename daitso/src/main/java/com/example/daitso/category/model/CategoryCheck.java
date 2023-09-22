package com.example.daitso.category.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCheck {
	private int categoryId;
	private String categoryNm;
	private String categoryContent;
	private int categoryPrId;
	private char status;
	private Date createDt;
	private String creator;
	private Date modifyDt;
	private String modifier;
	private int lv;
	private int isLeaf;
	
	private String path;
	private int rn;
	private int root_id;
	private int depth;
	private String categoryImage;
}
