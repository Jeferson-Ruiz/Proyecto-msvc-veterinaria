package com.jeferson.msvc_sav_workstaff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PhoneNumberRequestDto {
    
    @NotBlank(message =  "El numero de telefono no puede ser vacio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de teléfono solo puede contener dígitos sin espacios")
    private String phoneNumber;

}
