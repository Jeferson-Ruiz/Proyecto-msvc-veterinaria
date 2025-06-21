package com.jr.sav_msvc_patient_admission.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class petVaccineDto {
    private Long idPet;
    private String petName;
    private String vaccineName;
    private LocalDate applicationData;
    private LocalDate nextApplicationDate;
    private String observations;
}
