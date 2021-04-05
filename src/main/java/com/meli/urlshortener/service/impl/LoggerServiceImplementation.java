package com.meli.urlshortener.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.urlshortener.entity.EventRestModel;
import com.meli.urlshortener.service.LoggerService;
import com.meli.urlshortener.service.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class LoggerServiceImplementation implements LoggerService {

    @Autowired
    EventRepository eventRepository;

    @Override
    @Async("loggerThread")
    public CompletableFuture logData(ProceedingJoinPoint joinPoint, ResponseEntity responseData, Map<String, String> request) throws JsonProcessingException {
        EventRestModel event = null;
        ObjectMapper mapper =  new ObjectMapper();
        String method = joinPoint.getSignature().getName();
        Object[] argsArraey = joinPoint.getArgs();
        String queryParameters = (StringUtils.isEmpty(argsArraey.toString()) ? StringUtils.EMPTY :
                "/" + mapper.writeValueAsString(argsArraey));
        try {
            log.info("Request: method={}, URL={}, request-payload={}, Response: status={}, response-payload={}",
                    method, request.get("URL"),queryParameters, responseData.getStatusCode(), responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            event = logEventRest(method, request.get("URI"),
                    queryParameters, String.valueOf(responseData.getStatusCodeValue()),
                    mapper.writeValueAsString(responseData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(event);
    }

    private EventRestModel logEventRest(String method, String uri, String requestBody, String status, String responseBody) {
        EventRestModel event = new EventRestModel();
        event.setDate(LocalDateTime.now());
        event.setMethod(method);
        event.setRequestPayload(requestBody);
        event.setResponsePayload(StringUtils.truncate(responseBody, 255));
        event.setStatusCode(status);
        event.setUri(uri);

        return eventRepository.save(event);
    }
}
