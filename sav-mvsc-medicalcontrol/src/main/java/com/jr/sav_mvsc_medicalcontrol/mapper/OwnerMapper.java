package com.jr.sav_mvsc_medicalcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Owner;

@Mapper(componentModel="spring")
public interface OwnerMapper {
    
    Owner toEntity(OwnerRequestDto dto);
    
    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    OwnerResponseDto toDto(Owner entity);
}