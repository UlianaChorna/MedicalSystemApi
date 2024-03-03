package com.uliana.MedicalSystemApi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ReceptionDTO {
    private Long id;
    private Date date;
    @NotBlank
    private String medicines;
    @NotNull
    private List<Long> patients;
    @NotNull
    private Long doctorId;
}
