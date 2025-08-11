package com.jr.sav_mvsc_medicalcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(PetDto dto);

    PetDto toDto(Pet entity);

    PetResponseDto toResponsePetDto(Pet entity);

    @Mappings({
            @Mapping(source = "owner.name", target = "ownerName"),
            @Mapping(source = "owner.phoneNumber", target = "ownerPhone")
    })
    PetOwnerResponseDto toResponseDto(Pet entity);

}
