package com.meli.urlshortener.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface LoggerService {
    @Async("loggerThread")
    CompletableFuture logData(ProceedingJoinPoint joinPoint, ResponseEntity responseData, Map<String, String> request) throws JsonProcessingException;
}
