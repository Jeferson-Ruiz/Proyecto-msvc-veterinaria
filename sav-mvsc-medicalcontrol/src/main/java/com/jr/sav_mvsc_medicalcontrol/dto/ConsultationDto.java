package com.jr.sav_mvsc_medicalcontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDto {
    private Long idPet;
    private LocalDate date;
    private String reason;
    private String observations;
    private Long veterinaryId;
}
