package com.uliana.MedicalSystemApi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthcheckController {

    @Value("${app.subapp.myvar}")
    private String myVariable;
    @GetMapping("/healthcheck")
    public  String healthcheck() {
        log.info("I'm alive!");
        log.warn(myVariable);
        return  "I'm alive!!";
    }
}
