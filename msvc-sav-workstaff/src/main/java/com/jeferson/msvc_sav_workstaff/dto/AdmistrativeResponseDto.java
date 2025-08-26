package com.jeferson.msvc_sav_workstaff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmistrativeResponseDto extends EmployeeResponseDto{
    private String academicTitle;
    private String professionalCard;

}
