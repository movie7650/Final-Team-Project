package com.example.daitso.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daitso.cart.repository.ICartRepository;

@Service
public class CartService implements ICartService{
	
	@Autowired
	ICartRepository cartRepository;
}
