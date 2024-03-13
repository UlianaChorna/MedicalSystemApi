package com.uliana.MedicalSystemApi.util;

import com.uliana.MedicalSystemApi.dto.PatientDTO;
import com.uliana.MedicalSystemApi.dto.ReceptionDTO;
import com.uliana.MedicalSystemApi.entity.Patient;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class PatientDTOMapperUtil {

    public PatientDTO mapToDTO(Patient patient) {
        if (patient == null) {
            return null;
        }
        Set<ReceptionDTO> receptions = new HashSet<>();
        if (Objects.nonNull(patient.getReception())) {
            receptions = patient.getReception()
                    .stream()
                    .map(ReceptionDTOMapperUtil::mapToDTO)
                    .collect(Collectors.toSet());
        }

        return new PatientDTO()
                .setId(patient.getId())
                .setName(patient.getName())
                .setSurname(patient.getSurname())
                .setAge(patient.getAge())
                .setGender(patient.getGender())
                .setEmail(patient.getEmail())
                .setConfirmed(patient.isConfirmed())
                .setReceptions(receptions);

    }

    public Patient mapFromDTO(PatientDTO dto, String password) {

        return new Patient()
                .setId(dto.getId())
                .setName(dto.getName())
                .setAge(dto.getAge())
                .setEmail(dto.getEmail())
                .setGender(dto.getGender())
                .setPassword(password)
                .setConfirmed(false)
                .setSurname(dto.getSurname());
    }
}
