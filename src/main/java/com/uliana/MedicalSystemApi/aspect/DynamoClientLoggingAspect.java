package com.uliana.MedicalSystemApi.aspect;

import com.uliana.MedicalSystemApi.aws.DynamoClient;
import com.uliana.MedicalSystemApi.dto.DynamoRecordDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class DynamoClientLoggingAspect {


    private final DynamoClient dynamoClient;
    private final HttpServletRequest request;


    @Before("execution(* com.uliana.MedicalSystemApi.aws.DynamoClient.storeMessage(..)) && args(message)")
    public void beforeStoreMessage(JoinPoint joinPoint, String message) {
        logDetails(message, null, null);
    }

    @AfterReturning(pointcut = "execution(* com.uliana.MedicalSystemApi.aws.DynamoClient.storeMessage(..))", returning = "result")
    public void afterStoreMessage(JoinPoint joinPoint, Object result) {
        String responseBody = "Message stored successfully";
        logDetails(null, "200", responseBody);
    }

    @Before("execution(* com.uliana.MedicalSystemApi.aws.DynamoClient.getById(..)) && args(uuid)")
    public void beforeGetById(JoinPoint joinPoint, UUID uuid) {
        String requestBody = uuid.toString();
        logDetails(requestBody, null, null);
    }

    @AfterReturning(pointcut = "execution(* com.uliana.MedicalSystemApi.aws.DynamoClient.getById(..))", returning = "result")
    public void afterGetById(JoinPoint joinPoint, DynamoRecordDTO result) {
        String responseBody = result.toString();
        logDetails(null, "200", responseBody);
    }

    @AfterThrowing(pointcut = "execution(* com.uliana.MedicalSystemApi.aws.DynamoClient.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        String path = joinPoint.getSignature().toShortString();
        String errorMessage = ex.getMessage();
        dynamoClient.storeLogDetails(path, null, null, "500", null, errorMessage);
    }


    private void logDetails(String requestBody, String responseStatus, String responseBody) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        dynamoClient.storeLogDetails(path, method, requestBody, responseStatus, responseBody, null);
    }
}
