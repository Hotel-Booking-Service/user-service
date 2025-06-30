package com.hbs.userservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class RestLoggingAspect {

    @Around("@annotation(com.hbs.userservice.annotation.RestLoggableEndpoint)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("[ASPECT] ENTER: {} args={}", methodName, Arrays.toString(args));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("[ASPECT] EXCEPTION in {}: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }

        long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("[ASPECT] EXIT: {} result={} ({} ms)", methodName, result, elapsedTime);
        return result;
    }
}
