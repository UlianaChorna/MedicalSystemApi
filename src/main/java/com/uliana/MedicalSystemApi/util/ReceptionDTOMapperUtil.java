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
        ReceptionDTO receptionDTO = mapToDTO(reception);
        List<Long> patients = new ArrayList<>();
        if (Objects.nonNull(reception.getPatient())) {
            patients = reception.getPatient()
                    .stream()
                    .map(Patient::getId)
                    .collect(Collectors.toList());
        }

        Doctor doctor = reception.getDoctor();

        receptionDTO.setPatients(patients);
        receptionDTO.setDoctorId(doctor.getId());

        return receptionDTO;
    }

    public ReceptionDTO mapToDTO(Reception reception) {
        return new ReceptionDTO()
                .setId(reception.getId())
                .setDate(reception.getData())
                .setMedicines(reception.getMedicines());
    }

    public Reception mapFromDTO(ReceptionDTO dto, Set<Patient> patients, Doctor doctor) {

        return new Reception()
                .setId(dto.getId())
                .setData(dto.getDate())
                .setMedicines(dto.getMedicines())
                .setPatient(patients)
                .setDoctor(doctor);
    }
}
