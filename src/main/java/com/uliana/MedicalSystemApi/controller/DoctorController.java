package com.uliana.MedicalSystemApi.controller;

import com.uliana.MedicalSystemApi.dto.DoctorDTO;
import com.uliana.MedicalSystemApi.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDTO createDoctor(@RequestBody DoctorDTO dto){
        return doctorService.create(dto);
    }

    @GetMapping("/{id}")
    public DoctorDTO getById(@PathVariable Long id) {
        return doctorService.getById(id);
    }
}
