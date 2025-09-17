package com.jr.sav_mvsc_medicalcontrol.dto.pet;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.RemovalInfoResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetResponseDisableDto {
    private Long idPet;
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private byte age;

    private String fullName;
    private Long ownerPhone;

    private List<RemovalInfoResponseDto> removalInformation;
}
