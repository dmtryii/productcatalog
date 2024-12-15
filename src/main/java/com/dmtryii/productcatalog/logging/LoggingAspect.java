package com.dmtryii.productcatalog.logging;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.dmtryii..*Controller.*(..))")
    public void logRequest() {
        logger.info("Request received.");
    }

    @AfterReturning(pointcut = "execution(* com.dmtryii..*Controller.*(..))", returning = "response")
    public void logResponse(Object response) {
        logger.info("Response: {}", response);
    }
}
