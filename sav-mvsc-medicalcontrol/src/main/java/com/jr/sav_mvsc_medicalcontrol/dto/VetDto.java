package com.jr.sav_mvsc_medicalcontrol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetDto {
    private Long employeeId;
    private String name;
    private String lastName;
    private String documentNumber;
    private Boolean active;

}
