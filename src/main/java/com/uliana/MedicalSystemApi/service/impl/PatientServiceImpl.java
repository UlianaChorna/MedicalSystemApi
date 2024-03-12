package com.uliana.MedicalSystemApi.service.impl;

import com.uliana.MedicalSystemApi.dto.PatientDTO;
import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.exception.ResourceNotFoundException;
import com.uliana.MedicalSystemApi.repository.PatientRepository;
import com.uliana.MedicalSystemApi.service.PatientService;
import com.uliana.MedicalSystemApi.util.PatientDTOMapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.uliana.MedicalSystemApi.util.ExceptionMessagesUtil.PATIENT_FOUND_MESSAGE;

@AllArgsConstructor
@Slf4j
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Transactional
    public PatientDTO create(PatientDTO dto) {
        Patient patient = PatientDTOMapperUtil.mapFromDTO(dto);
        patient  = patientRepository.save(patient);
        sendConfirmationEmail(patient);

        return PatientDTOMapperUtil.mapToDTO(patient);
    }

    @Override
    public PatientDTO getById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PATIENT_FOUND_MESSAGE,id)
        );
        return PatientDTOMapperUtil.mapToDTO(patient);
    }

    public void sendConfirmationEmail(Patient patient) {
        if (Objects.isNull(patient) || Objects.isNull(patient.getEmail())) {
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(patient.getEmail());
        message.setSubject("Підтвердження реєстрації");
        message.setText("Для підтвердження реєстрації перейдіть за посиланням: http://localhost:8080/patient/confirm?id=" + patient.getId());
        javaMailSender.send(message);
    }
    @Override
    @Transactional
    public void confirmRegistration(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(PATIENT_FOUND_MESSAGE, id)
        );
        patient.setConfirmed(true);
        patientRepository.save(patient);
    }
}
