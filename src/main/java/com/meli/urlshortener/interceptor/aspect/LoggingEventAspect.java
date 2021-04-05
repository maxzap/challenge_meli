package com.meli.urlshortener.interceptor.aspect;

import com.meli.urlshortener.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class LoggingEventAspect {

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    LoggerService loggerService;


    @Around("execution(* com.meli.urlshortener.controller.ShortenUrlController.*(..))")
    public Object logAroundEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        Object objectResponse = joinPoint.proceed();
        loggerService.logData(joinPoint, (ResponseEntity) objectResponse, getRequestData());
        return objectResponse;
    }

    private Map<String, String> getRequestData() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("URL", request.getRequestURL().toString());
        requestData.put("URI", request.getRequestURI());

        return requestData;
    }
}
