package com.jr.sav_mvsc_medicalcontrol.dto.pet;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetOwnerResponseDto {
    private Long idPet;
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private LocalDate dateOfBirth;
    private String ownerName;
    private Long ownerPhone;
}