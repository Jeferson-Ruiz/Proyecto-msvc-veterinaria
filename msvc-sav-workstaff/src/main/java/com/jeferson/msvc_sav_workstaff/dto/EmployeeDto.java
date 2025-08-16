package com.jeferson.msvc_sav_workstaff.dto;

import java.time.LocalDate;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
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
    private String documentNumber;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private ContractType contractType;
}
