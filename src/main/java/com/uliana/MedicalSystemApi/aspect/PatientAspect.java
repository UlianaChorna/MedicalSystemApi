package com.uliana.MedicalSystemApi.aspect;


import com.uliana.MedicalSystemApi.dto.PatientDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PatientAspect {
    @Before("execution(* com.uliana.MedicalSystemApi.service.PatientService.create(..)) && args(dto)")
    public void logCreating(PatientDTO dto) {
        log.info("Creating patient: {}", dto);
    }

    @AfterReturning(pointcut = "execution(* com.uliana.MedicalSystemApi.service.PatientService.getById(..))", returning = "dto")
    public void logResult(PatientDTO dto) {
        log.info("Result: {}", dto);
    }

    @Around("execution(* com.uliana.MedicalSystemApi.service.PatientService.*(..))")
    public Object measuringExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.info("Execution time:{}, whit time: {}", joinPoint.getSignature().getName(),(endTime - startTime) );
        return  result;
    }
}
