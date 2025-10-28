package com.jr.msvc.medicalcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerRequestDto;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerResponseDto;
import com.jr.msvc.medicalcontrol.models.Owner;

@Mapper(componentModel="spring",  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {
    
    Owner toEntity(OwnerRequestDto dto);
    
    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    OwnerResponseDto toDto(Owner entity);
}