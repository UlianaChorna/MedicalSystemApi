package com.uliana.MedicalSystemApi.controller;

import com.uliana.MedicalSystemApi.dto.ReceptionDTO;
import com.uliana.MedicalSystemApi.service.ReceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reception")
public class ReceptionController {
    private final ReceptionService receptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReceptionDTO createReception(@RequestBody ReceptionDTO dto) {
        return receptionService.create(dto);
    }

    @GetMapping("/{id}")
    public ReceptionDTO getById(@PathVariable Long id) {
        return receptionService.getById(id);
    }

    @PatchMapping("/{id}/patient/{patientId}")
    public ReceptionDTO addPatientToReception(@PathVariable Long id, @PathVariable Long patientId) {
        return receptionService.addPatientToReception(id, patientId);
    }
}
