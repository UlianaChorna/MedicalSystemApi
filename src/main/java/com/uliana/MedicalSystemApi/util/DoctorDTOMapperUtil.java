package com.uliana.MedicalSystemApi.util;

import com.uliana.MedicalSystemApi.dto.DoctorDTO;
import com.uliana.MedicalSystemApi.entity.Doctor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DoctorDTOMapperUtil {

    public DoctorDTO mapToDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        return new DoctorDTO()
                .setId(doctor.getId())
                .setName(doctor.getName())
                .setSurname(doctor.getSurname())
                .setSpecialty(doctor.getSpecialty());
    }

    public Doctor mapFromDTO(DoctorDTO dto) {
        return new Doctor()
                .setId(dto.getId())
                .setName(dto.getName())
                .setSurname(dto.getSurname())
                .setSpecialty(dto.getSpecialty());
    }
}
