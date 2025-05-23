package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeDto;
import com.jeferson.msvc_sav_workstaff.models.Administrative;

@Mapper(componentModel = "spring")
public interface AdministrativeMapper {

    Administrative toEntity(AdministrativeDto dto);
    AdministrativeDto toDto(Administrative entity);

}
