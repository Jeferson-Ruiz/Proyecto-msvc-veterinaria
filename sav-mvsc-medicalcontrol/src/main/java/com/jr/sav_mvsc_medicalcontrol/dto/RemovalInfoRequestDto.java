package com.jr.sav_mvsc_medicalcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemovalInfoRequestDto {

    @NotBlank(message =  "El numero de identificacion no puede ser vacio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de identificacion solo puede contener dígitos sin espacios")
    private String deletedBy;

    @NotBlank(message = "El motivo de eliminacion del paciente no puede ser nulo")
    private String reason;

}
