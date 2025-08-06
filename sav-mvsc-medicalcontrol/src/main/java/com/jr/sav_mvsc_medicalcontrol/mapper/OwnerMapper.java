package com.jr.sav_mvsc_medicalcontrol.mapper;

import org.mapstruct.Mapper;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerDto;
import com.jr.sav_mvsc_medicalcontrol.models.Owner;

@Mapper(componentModel="spring")
public interface OwnerMapper {
    
    Owner toEntity(OwnerDto dto);
    OwnerDto toDto(Owner entity);
}