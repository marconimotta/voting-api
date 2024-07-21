package com.sicredi.voting.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.sicredi.voting.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	
	 private static final String INIT_MESSAGE = "c={} m={} stage=INIT args={}";
	    private static final String END_MESSAGE = "c={} m={} stage=END result={}";

	    @Around("execution(* com.sicredi.voting..*Controller*.*(..)) || execution(* com.sicredi.voting..*Service*.*(..))")
	    public Object logMethodCall(final ProceedingJoinPoint joinPoint) throws Throwable {
	        return handleJoinPoint(joinPoint);
	    }

	    private Object handleJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
	        final String methodName = joinPoint.getSignature().getName();
	        final String className = removePackage(joinPoint.getSignature().getDeclaringType().getName());
	        final Object[] args = joinPoint.getArgs();
	        final String jsonArgs = JsonUtil.convertToJson(args);
	        log.info(INIT_MESSAGE, className, methodName, jsonArgs);
	        final Object result = joinPoint.proceed();
	        final String jsonResult = JsonUtil.convertToJson(result);
	        log.info(END_MESSAGE, className, methodName, jsonResult);
	        return result;
	    }

	    private static String removePackage(String className) {
	        return className.substring(className.lastIndexOf(".") + 1);
	    }

}
