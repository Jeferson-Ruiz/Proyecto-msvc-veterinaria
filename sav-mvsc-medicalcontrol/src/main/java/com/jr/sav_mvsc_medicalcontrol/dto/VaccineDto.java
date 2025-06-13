package com.jr.sav_mvsc_medicalcontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineDto {
    private Long idPet;
    private String vaccineName;
    private LocalDate applicationData;
    private LocalDate nextApplicationDate;
    private String lot;
    private String observations;

}
