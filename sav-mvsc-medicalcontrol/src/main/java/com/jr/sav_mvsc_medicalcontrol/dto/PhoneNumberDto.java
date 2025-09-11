package com.jr.sav_mvsc_medicalcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberDto {

    @NotBlank(message =  "El numero de telefono no puede ser vacio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de teléfono solo puede contener dígitos sin espacios")
    private String phoneNumber;

}
