package com.uliana.MedicalSystemApi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
public class DoctorDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotBlank
    @Size(min = 5)
    private String specialty;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDTO doctorDTO = (DoctorDTO) o;
        return Objects.equals(id, doctorDTO.id) &&
                Objects.equals(name, doctorDTO.name) &&
                Objects.equals(surname, doctorDTO.surname) &&
                Objects.equals(specialty, doctorDTO.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, specialty);
    }

}
