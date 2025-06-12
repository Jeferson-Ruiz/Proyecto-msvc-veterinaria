package com.jr.sav_msvc_patient_admission.mapper;

import org.mapstruct.Mapper;

import com.jr.sav_msvc_patient_admission.dto.OwnerDto;
import com.jr.sav_msvc_patient_admission.models.Owner;

@Mapper(componentModel="spring")
public interface OwnerMapper {

    Owner toEntity(OwnerDto dto);
    OwnerDto toDto(Owner entity);

}