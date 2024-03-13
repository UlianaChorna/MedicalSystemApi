package com.uliana.MedicalSystemApi.controller;

import com.uliana.MedicalSystemApi.dto.LoginRequestDTO;
import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.exception.AuthenticationException;
import com.uliana.MedicalSystemApi.security.JwtTokenProvider;
import com.uliana.MedicalSystemApi.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(summary = "Get a JWT token for registration user")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequestDTO loginRequestDto) {
        Patient patient = authenticationService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        try {
            String token = jwtTokenProvider.createToken(patient.getEmail());
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now().toString());
            body.put("token", token);
            body.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid email or password!");
        }
    }
}
