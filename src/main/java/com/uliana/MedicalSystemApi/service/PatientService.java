package com.uliana.MedicalSystemApi.service;

import com.uliana.MedicalSystemApi.dto.PatientDTO;
import com.uliana.MedicalSystemApi.entity.Patient;

public interface PatientService extends BaseCRUDService<PatientDTO> {

    void sendConfirmationEmail(Patient patient);

    void confirmRegistration(Long id);
}
