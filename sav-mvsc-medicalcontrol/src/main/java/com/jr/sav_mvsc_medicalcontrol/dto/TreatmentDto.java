package com.jr.sav_mvsc_medicalcontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDto {
    private Long idConsultation;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String medicines;
    private String instructions;
}
