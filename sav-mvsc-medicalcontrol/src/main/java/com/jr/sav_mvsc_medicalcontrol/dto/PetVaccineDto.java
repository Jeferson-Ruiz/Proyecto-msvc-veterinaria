package com.jr.sav_mvsc_medicalcontrol.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetVaccineDto {
    private Long idPet;
    private String petName;
    private String vaccineName;
    private LocalDate applicationData;
    private LocalDate nextApplicationDate;
    private String observations;
}