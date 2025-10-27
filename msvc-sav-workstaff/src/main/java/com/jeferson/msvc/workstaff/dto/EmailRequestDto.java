package com.jeferson.msvc.workstaff.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailRequestDto {

    @NotBlank(message = "El email no puede ser vacio")
    @Email(message = "El formato del email no es válido")
    private String email;
}