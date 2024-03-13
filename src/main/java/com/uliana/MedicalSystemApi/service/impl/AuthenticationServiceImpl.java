package com.uliana.MedicalSystemApi.service.impl;

import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.exception.AuthenticationException;
import com.uliana.MedicalSystemApi.repository.PatientRepository;
import com.uliana.MedicalSystemApi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Patient login(String email, String password) throws AuthenticationException {
        Optional<Patient> user = patientRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        throw new AuthenticationException("Incorrect username or password!!!");
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
