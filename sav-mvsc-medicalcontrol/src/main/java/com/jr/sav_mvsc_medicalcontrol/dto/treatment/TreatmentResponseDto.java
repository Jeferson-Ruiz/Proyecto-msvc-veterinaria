package com.jr.sav_mvsc_medicalcontrol.dto.treatment;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentResponseDto {
    private Long idTreatment;
    private String name;
    private String description;
    private LocalDate registrationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String instructions;

}
