package com.dmtryii.productcatalog.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.dmtryii.productcatalog.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Executing method: {} with arguments: {}",
                joinPoint.getSignature(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.dmtryii.productcatalog.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method executed: {} with result: {}",
                joinPoint.getSignature(),
                result);
    }

    @AfterThrowing(pointcut = "execution(* com.dmtryii.productcatalog.service.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in method: {} with cause: {}",
                joinPoint.getSignature(),
                exception.getMessage(), exception);
    }
}
