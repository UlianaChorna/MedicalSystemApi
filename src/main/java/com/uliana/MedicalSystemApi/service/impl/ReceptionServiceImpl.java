package com.uliana.MedicalSystemApi.service.impl;

import com.uliana.MedicalSystemApi.dto.ReceptionDTO;
import com.uliana.MedicalSystemApi.entity.Doctor;
import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.entity.Reception;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import com.uliana.MedicalSystemApi.repository.DoctorRepository;
import com.uliana.MedicalSystemApi.repository.PatientRepository;
import com.uliana.MedicalSystemApi.repository.ReceptionRepository;
import com.uliana.MedicalSystemApi.service.ReceptionService;
import com.uliana.MedicalSystemApi.util.ExceptionMessagesUtil;
import com.uliana.MedicalSystemApi.util.ReceptionDTOMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.uliana.MedicalSystemApi.util.ExceptionMessagesUtil.DOCTOR_FOUND_MESSAGE;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReceptionServiceImpl implements ReceptionService {
    private final ReceptionRepository receptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    @Override
    @Transactional
    public ReceptionDTO create(ReceptionDTO receptionDTO) {
        Doctor doctor = doctorRepository.findById(receptionDTO.getDoctorId()).orElseThrow(
                () -> new ResourceNotFoundException(DOCTOR_FOUND_MESSAGE, receptionDTO.getDoctorId())
        );

        Set<Patient> patients = getPatients(receptionDTO.getPatients());

        Reception reception = ReceptionDTOMapperUtil.mapFromDTO(receptionDTO, patients, doctor);
        reception = receptionRepository.save(reception);

        return ReceptionDTOMapperUtil.mapToFullDTO(reception);
    }

    @Override
    public ReceptionDTO getById(Long id) {
      Reception reception = receptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessagesUtil.RECEPTION_FOUND_MESSAGE,id));
        return ReceptionDTOMapperUtil.mapToFullDTO(reception);
    }

    Set<Patient> getPatients(List<Long> patientIdList) {
        if (patientIdList == null) {
            return new HashSet<>();
        }
        return patientIdList.stream()
                .map(patientRepository::findById)
                .map(optional -> optional.orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }


    @Override
    @Transactional
    public ReceptionDTO addPatientToReception(Long id, Long patientId) {
        Reception reception = receptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessagesUtil.RECEPTION_FOUND_MESSAGE,id));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessagesUtil.PATIENT_FOUND_MESSAGE, patientId));

        Set<Patient> patients = reception.getPatient();
        patients.add(patient);
        reception.setPatient(patients);

        reception = receptionRepository.save(reception);
        return ReceptionDTOMapperUtil.mapToFullDTO(reception);
    }
}
