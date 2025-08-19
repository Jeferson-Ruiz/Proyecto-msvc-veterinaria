package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.Administrative;

@Mapper(componentModel = "spring")
public interface AdministrativeMapper {

    Administrative toEntity(AdministrativeRequestDto dto);
    AdmistrativeResponseDto toDto(Administrative entity);

}
