package com.jr.sav_msvc_patient_admission.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PetResponseDto {
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private LocalDate dateOfBirth;
    private LocalDate dateOfRecording;
    private String ownerName;
    private Long ownerPhone;

}
