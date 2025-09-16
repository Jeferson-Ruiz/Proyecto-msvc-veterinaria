package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc_sav_workstaff.exeptions.DocumentNumberId;
import com.jeferson.msvc_sav_workstaff.exeptions.PhoneNumber;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.DocumentType;
import com.jeferson.msvc_sav_workstaff.models.VetRoles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotNull
    private DocumentType documentType;

    @DocumentNumberId @NotBlank
    private String documentNumber;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @NotBlank @Email
    private String email;

    @NotBlank @PhoneNumber
    private String phoneNumber;

    @NotNull
    private ContractType contractType;

    @NotNull
    private VetRoles vetRoles;

    @Pattern(
    regexp = "^(?!\\s*$).+",
        message = "El campo no puede estar vacío o contener solo espacios en blanco")
    private String speciality;
 
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", 
         message = "La tarjeta profesional no puede ser nula, ni caracteres contener caracteres vacios")
    private String professionalCard;
    
}
