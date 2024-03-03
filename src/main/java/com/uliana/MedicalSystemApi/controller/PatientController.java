package com.uliana.MedicalSystemApi.controller;

import com.uliana.MedicalSystemApi.dto.PatientDTO;
import com.uliana.MedicalSystemApi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO createPatient(@RequestBody PatientDTO dto){
        return patientService.create(dto);
    }

    @GetMapping("/{id}")
    public PatientDTO getById(@PathVariable Long id) {
        return patientService.getById(id);
    }

}
