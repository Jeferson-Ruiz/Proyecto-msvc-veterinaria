package com.jeferson.msvc.workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc.workstaff.exeptions.DocumentNumberId;
import com.jeferson.msvc.workstaff.exeptions.PhoneNumber;
import com.jeferson.msvc.workstaff.models.AdministrativeRoles;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.DocumentType;
import jakarta.validation.constraints.Email;
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

    @NotBlank @Email
    private String email;
    
    @PhoneNumber @NotBlank
    private String phoneNumber;

    @NotNull
    private ContractType contractType;

    @NotNull
    private AdministrativeRoles administrativeRoles;
    
    @NotBlank
    private String academicTitle;

    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$",
    message = "La tarjeta profesional solo puede contener números y letras")
    private String professionalCard;

}
