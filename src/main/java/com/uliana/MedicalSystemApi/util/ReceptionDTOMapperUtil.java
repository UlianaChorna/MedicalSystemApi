package com.uliana.MedicalSystemApi.util;

import com.uliana.MedicalSystemApi.dto.ReceptionDTO;
import com.uliana.MedicalSystemApi.entity.Doctor;
import com.uliana.MedicalSystemApi.entity.Patient;
import com.uliana.MedicalSystemApi.entity.Reception;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ReceptionDTOMapperUtil {
    public ReceptionDTO mapToFullDTO(Reception reception) {
        if (reception == null) {
            return null;
        }

        ReceptionDTO receptionDTO = mapToDTO(reception);

        Doctor doctor = reception.getDoctor();

        if (doctor != null) {
            receptionDTO.setDoctorId(doctor.getId());
        }

        List<Long> patients = new ArrayList<>();
        if (Objects.nonNull(reception.getPatient())) {
            patients = reception.getPatient()
                    .stream()
                    .map(Patient::getId)
                    .collect(Collectors.toList());
        }

        receptionDTO.setPatients(patients);

        return receptionDTO;
    }

    public ReceptionDTO mapToDTO(Reception reception) {
        if (reception == null) {
            return null;
        }

        return new ReceptionDTO()
                .setId(reception.getId())
                .setDate(reception.getData())
                .setMedicines(reception.getMedicines());
    }

    public Reception mapFromDTO(ReceptionDTO dto, Set<Patient> patients, Doctor doctor) {
        if (dto == null) {
            return null;
        }
        return new Reception()
                .setId(dto.getId())
                .setData(dto.getDate())
                .setMedicines(dto.getMedicines())
                .setPatient(patients)
                .setDoctor(doctor);
    }
}
