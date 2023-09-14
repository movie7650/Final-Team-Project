package com.example.daitso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DaitsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaitsoApplication.class, args);
	}

}
