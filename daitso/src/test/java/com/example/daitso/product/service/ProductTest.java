package com.example.daitso.product.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.daitso.product.model.SpecialProduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Transactional
public class ProductTest {
	
	@Autowired
	IProductService productService;
	
	@Test
	@Transactional
	void 인기및할인상품등록() {
		//given
		List<SpecialProduct> listBefore = productService.selectSpecialProduct();
		//when
		productService.insertSpecialProduct();
		
		//then
		List<SpecialProduct> listAfter = productService.selectSpecialProduct();
		assertThat(listAfter.size()).isEqualTo(listBefore.size() + 10);
	}
}
