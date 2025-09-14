package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc_sav_workstaff.exeptions.DocumentNumberId;
import com.jeferson.msvc_sav_workstaff.exeptions.PhoneNumber;
import com.jeferson.msvc_sav_workstaff.models.AdministrativeRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministrativeRequestDto {

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

    @NotBlank
    private String email;
    
    @PhoneNumber @NotBlank
    private String phoneNumber;

    @NotNull
    private ContractType contractType;

    @NotNull
    private AdministrativeRoles administrativeRoles;
    
    @NotBlank
    private String academicTitle;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", 
         message = "La tarjeta profesional solo puede contener numeros y letras, sin espacios en blanco ni caracteres especiales")
    private String professionalCard;

}
