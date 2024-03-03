package com.uliana.MedicalSystemApi.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class PatientDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String gender;
    @Positive
    @Max(value = 110)
    private Integer age;
    private Set<ReceptionDTO> receptions;
}
