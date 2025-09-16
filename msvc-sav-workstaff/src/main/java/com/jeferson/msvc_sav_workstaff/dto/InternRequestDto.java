package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc_sav_workstaff.exeptions.DocumentNumberId;
import com.jeferson.msvc_sav_workstaff.exeptions.PhoneNumber;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.DocumentType;
import com.jeferson.msvc_sav_workstaff.models.InternRoles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternRequestDto{

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotNull
    private DocumentType documentType;

    @DocumentNumberId @NotBlank
    private String documentNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @Email @NotBlank
    private String email;

    @PhoneNumber @NotBlank
    private String phoneNumber;

    @NotNull
    private ContractType contractType;

    @NotNull
    private InternRoles internRoles;

    @NotBlank
    private String educationInstitute;

    @NotBlank
    private String levelAcademic;

    @NotBlank
    private String trainingCareer;

}
