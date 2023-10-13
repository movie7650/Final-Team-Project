package com.example.daitso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.daitso.aop.Aspect;

@EnableScheduling
@SpringBootApplication
@ServletComponentScan
@Import(Aspect.class)
public class DaitsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaitsoApplication.class, args);
	}

}
