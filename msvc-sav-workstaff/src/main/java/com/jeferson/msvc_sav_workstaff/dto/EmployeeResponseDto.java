package com.jeferson.msvc_sav_workstaff.dto;

import com.jeferson.msvc_sav_workstaff.models.DocumentType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDto {
    private Long employeeId;
    private String fullName;
    private DocumentType documentType;
    private String documentNumber;
    private String email;
    private String phoneNumber;
    private WorkArea workArea;
    private Boolean active;
}
