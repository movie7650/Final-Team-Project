package com.example.daitso.category.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryPaging {
	private int totalPage;
	private int page;
	private int totalPageBlock;
	private int startPage;
	private int endPage;
	private int nowPageBlock;
}
