package com.uliana.MedicalSystemApi.service;

import com.uliana.MedicalSystemApi.entity.Patient;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    Patient login(String email, String password) throws AuthenticationException;

    String encodePassword(String password);
}
