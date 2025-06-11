package com.jr.sav_msvc_pet.mapper;

import org.mapstruct.Mapper;
import com.jr.sav_msvc_pet.dto.OwnerDto;
import com.jr.sav_msvc_pet.models.Owner;

@Mapper(componentModel="spring")
public interface OwnerMapper {

    Owner toEntity(OwnerDto dto);
    OwnerDto toDto(Owner entity);

}