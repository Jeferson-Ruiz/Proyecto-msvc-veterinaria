package com.jeferson.msvc.workstaff.dto;

import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.WorkArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDto {
    private String employeeCode;
    private String fullName;
    private String documentNumber;
    private String email;
    private String phoneNumber;
    private WorkArea workArea;
    private EmployeeStatus status;
}
