package com.jr.sav_msvc_pet.mapper;

import org.mapstruct.Mapper;
import com.jr.sav_msvc_pet.dto.PetDto;
import com.jr.sav_msvc_pet.models.Pet;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(PetDto dto);
    PetDto toDto(Pet entity);

}