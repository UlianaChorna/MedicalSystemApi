package com.uliana.MedicalSystemApi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
}
