package com.jr.sav_mvsc_medicalcontrol.dto.pet;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetRequestDto {
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private LocalDate dateOfBirth;
    private Long documentNumber;
}