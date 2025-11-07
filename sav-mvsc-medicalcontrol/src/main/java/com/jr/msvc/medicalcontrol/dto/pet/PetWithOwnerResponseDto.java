package com.jr.msvc.medicalcontrol.dto.pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetWithOwnerResponseDto {
    private String petCode;
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private byte age;

    private String fullName;
    private Long ownerPhone;
}