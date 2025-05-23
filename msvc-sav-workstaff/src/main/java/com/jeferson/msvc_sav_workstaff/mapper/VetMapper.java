package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.VetDto;
import com.jeferson.msvc_sav_workstaff.models.Vet;

@Mapper(componentModel = "spring")
public interface VetMapper {

    Vet toEntity(VetDto dto);

    VetDto toDto(Vet entity);

}
