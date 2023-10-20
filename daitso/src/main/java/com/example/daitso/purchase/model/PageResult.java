package com.example.daitso.purchase.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResult<T> {
    private List<T> data;
    private int currentPage;
    private int totalPages;
    private int totalCount;
}
