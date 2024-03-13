package com.uliana.MedicalSystemApi.security;

import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.service.PatientService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PatientService patientService;

    public UserDetailsServiceImpl(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Patient> userOptional = patientService.findByEmail(email);
        User.UserBuilder builder;
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Patient not found.");
        }

        builder = org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(userOptional.get().getPassword());

        return builder.build();
    }
}
