package com.uliana.MedicalSystemApi.service;

import com.uliana.MedicalSystemApi.dto.ReceptionDTO;

public interface ReceptionService extends BaseCRUDService<ReceptionDTO> {
    ReceptionDTO addPatientToReception(Long id, Long patientId);
}
