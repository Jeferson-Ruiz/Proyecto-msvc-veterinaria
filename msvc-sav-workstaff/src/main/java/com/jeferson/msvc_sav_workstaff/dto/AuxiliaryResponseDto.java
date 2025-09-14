package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc_sav_workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.DocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuxiliaryResponseDto {

    private Long employeeId;
    private String fullName;
    private DocumentType documentType;
    private String documentNumber;
    private String email;
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private Byte age;
    private ContractType contractType;
    private AuxiliaryRoles auxiliaryRoles;
    private String academicCertificate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;

    private Boolean active;

}
