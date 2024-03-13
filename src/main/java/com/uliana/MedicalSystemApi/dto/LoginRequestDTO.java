package com.uliana.MedicalSystemApi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank(message = "can't be blank")
    private String email;
    @NotBlank(message = "can't be blank")
    private String password;
}
