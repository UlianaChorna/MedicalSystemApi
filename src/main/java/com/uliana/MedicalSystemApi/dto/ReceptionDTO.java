package com.uliana.MedicalSystemApi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceptionDTO that = (ReceptionDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(medicines, that.medicines) && Objects.equals(patients, that.patients) && Objects.equals(doctorId, that.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, medicines, patients, doctorId);
    }
}
