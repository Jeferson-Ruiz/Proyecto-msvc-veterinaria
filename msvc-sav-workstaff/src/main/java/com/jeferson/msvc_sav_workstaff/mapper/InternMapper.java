package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.InternResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.models.Intern;

@Mapper(componentModel = "spring")
public interface InternMapper {

    Intern toEntity(InternRequestDto dto);
    InternResponseDto toDto(Intern entity);
}
