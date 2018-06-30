/**
 * 
 */
package com.mps.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sandeep
 *
 */
@Component
@Aspect
@RestControllerAdvice
@Slf4j
public class ControllerAspect {

	@Before("@annotation(com.mps.app.annotations.RestMethodAdvice)")
	public void logRestBody(JoinPoint joinPoint) {
		log.info("Kind of Joint Point : " + joinPoint.getKind());
		log.info("Method Name : " + joinPoint.getSignature().getName());
		log.info("Value Passed: " + joinPoint.getArgs()[0]);

		log.info(String.valueOf((System.currentTimeMillis())));
	}

	@Around("@annotation(com.mps.app.annotations.RestMethodAdvice)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
		return proceed;
	}

	@Before(value = "execution(* com.mps.app.rest.*.delete*(*))")
	public void logPackageCalls(JoinPoint joinPoint) {
		log.info("Kind of Joint Point : " + joinPoint.getKind());
		log.info("Method Name : " + joinPoint.getSignature().getName());
		log.info("Value Passed: " + joinPoint.getArgs()[0]);

		log.info(String.valueOf((System.currentTimeMillis())));
	}
}
