package com.jeferson.msvc.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    @Email(message = "formato correo invalido")
    @NotBlank(message = "El correa a actualizar no puede ser vacio")
    private String email;

}
