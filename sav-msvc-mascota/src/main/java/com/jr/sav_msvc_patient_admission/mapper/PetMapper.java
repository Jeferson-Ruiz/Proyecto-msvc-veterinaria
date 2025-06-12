package com.jr.sav_msvc_patient_admission.mapper;

import org.mapstruct.Mapper;

import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.models.Pet;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(PetDto dto);
    PetDto toDto(Pet entity);

}