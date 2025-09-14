package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc_sav_workstaff.exeptions.DocumentNumberId;
import com.jeferson.msvc_sav_workstaff.exeptions.PhoneNumber;
import com.jeferson.msvc_sav_workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuxiliaryRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotNull
    private DocumentType documentType;

    @DocumentNumberId @NotNull
    private String documentNumber;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @NotBlank
    private String email;

    @PhoneNumber @NotNull
    private String phoneNumber;

    @NotNull
    private ContractType contractType;

    @NotNull
    private AuxiliaryRoles auxiliaryRoles;

    @NotBlank
    private String academicCertificate;

}
