package com.jr.msvc.medicalcontrol.dto.removal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemovalInfoRequestDto {

    @NotBlank(message =  "El numero de identificacion no puede ser vacio")
    @Pattern(
        regexp = "^[A-Z0-9-]+$",
        message = "El número de identificación solo puede contener letras mayúsculas, números y el símbolo '-' sin espacios")
    private String deletedBy;

    @NotBlank(message = "El motivo de eliminacion del paciente no puede ser nulo")
    private String reason;

}
