package com.jr.msvc.medicalcontrol.dto.vet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetResponseDto {
    private Long employeeId;
    private String fullName;
    private String status;
}
