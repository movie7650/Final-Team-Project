package com.example.daitso.customer.model;

import lombok.Getter;

@Getter
public enum CustomerRole {
	ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    CustomerRole(String value) {
        this.value = value;
    }

    private String value;
}
