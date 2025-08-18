package com.jeferson.msvc_sav_workstaff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VetRequestDto extends EmployeeRequestDto {
    private String speciality;
    private String professionalCard;
    
}
