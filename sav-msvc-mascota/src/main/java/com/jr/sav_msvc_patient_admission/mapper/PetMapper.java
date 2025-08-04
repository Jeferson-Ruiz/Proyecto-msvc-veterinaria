package com.jr.sav_msvc_patient_admission.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.dto.PetOwnerResponseDto;
import com.jr.sav_msvc_patient_admission.dto.PetResponseDto;
import com.jr.sav_msvc_patient_admission.models.Pet;

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