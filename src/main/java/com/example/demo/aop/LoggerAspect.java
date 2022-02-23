package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution( * com.example.demo..controller.*Controller.*(..))"
			+ " or execution(* com.example.demo..service.*Impl.*(..))"
			+ " or execution(* com.example.demo..mapper.*Mapper.*(..))")
	public Object pringLog(ProceedingJoinPoint joinPoint) throws Throwable{
		
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		
		if(name.contains("Controller") == true) {
			type = "Controller ==> ";
		}else if(name.contains("Service") == true) {
			type = "Service ==> ";
		}else if(name.contains("Mapper") == true) {
			type = "Mapper ==> ";
		}
		logger.debug(type + name + "." + joinPoint.getSignature().getName()+ "()");
		
		return joinPoint.proceed();
	}
}
