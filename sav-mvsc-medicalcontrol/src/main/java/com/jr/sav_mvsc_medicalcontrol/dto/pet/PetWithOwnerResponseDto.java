package com.jr.sav_mvsc_medicalcontrol.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetWithOwnerResponseDto {
    private Long idPet;
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private byte age;
    private String ownerName;
    private Long ownerPhone;
}