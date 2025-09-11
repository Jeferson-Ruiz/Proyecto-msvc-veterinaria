package com.jr.sav_mvsc_medicalcontrol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotNull
    private String documentType;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$", 
         message = "El numero de documento solo puede contener numeros, sin espacios en blanco ni caracteres especiales")
    private String documentNumber;

    @Pattern( regexp = "^$|^(?!\\s+$).+", message = "El email no puede contener solo espacios") 
    @Email(message = "Formato de email inválido")
    private String email;

    @Pattern(regexp = "^$|^[0-9]+$", 
        message = "Solo se permiten números (sin espacios ni caracteres especiales)")
    private String phoneNumber;

}