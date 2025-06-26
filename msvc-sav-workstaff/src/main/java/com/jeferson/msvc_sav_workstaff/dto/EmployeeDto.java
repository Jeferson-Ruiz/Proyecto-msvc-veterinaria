package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private String name;
    private String lastName;
    private String documentType;
    private Long documentNumber;
    private LocalDate dateOfBirth;
    private String email;
    private Long phoneNumber;
    private ContractType contractType;
    private Boolean workStatus;
}
