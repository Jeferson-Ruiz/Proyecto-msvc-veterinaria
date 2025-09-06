package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.DocumentType;
import com.jeferson.msvc_sav_workstaff.models.InternRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternResponseDto {

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
    private InternRoles internRoles;
    private String educationInstitute;
    private String levelAcademic;
    private String trainingCareer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;

    private Boolean active;
}
