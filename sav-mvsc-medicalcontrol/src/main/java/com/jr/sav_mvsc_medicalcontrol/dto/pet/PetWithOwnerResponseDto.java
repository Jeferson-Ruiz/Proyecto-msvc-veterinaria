package com.jr.sav_mvsc_medicalcontrol.dto.pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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