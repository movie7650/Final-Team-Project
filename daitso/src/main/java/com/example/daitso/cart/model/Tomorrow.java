package com.example.daitso.cart.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Tomorrow {
	private String mad; // 월,일
	private String dof; // 요일
}
