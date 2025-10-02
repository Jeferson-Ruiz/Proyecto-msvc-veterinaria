package com.jeferson.msvc.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteRequestDto {
    @NotBlank(message = "La razon del cambio de estado no puede ser nula")
    private String reason;

}
