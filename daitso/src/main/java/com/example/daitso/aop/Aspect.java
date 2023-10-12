package com.example.daitso.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.aspectj.lang.annotation.Aspect
public class Aspect {

	@Pointcut("execution(* com.example.daitso..*(..))")
	private void allController() {}

	@Around("allController()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature());
		try {
			return joinPoint.proceed();
		} catch (Exception e) {
			log.info("[error] {}", joinPoint.getSignature());
			throw e;
		}
	}
}
