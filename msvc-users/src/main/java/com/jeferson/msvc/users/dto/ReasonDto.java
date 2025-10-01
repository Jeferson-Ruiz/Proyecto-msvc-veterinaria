package com.jeferson.msvc.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReasonDto {
    
    @NotBlank(message = "La razon no puede ser nula")
    private String reason;

}
