package com.jr.sav_mvsc_medicalcontrol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

    @NotBlank(message = "El email no puede estar vacío ni contener solo espacios")
    @Email(message = "El formato del email no es válido")
    private String email;

}
