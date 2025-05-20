package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.models.Intern;

@Mapper(componentModel = "spring")
public interface InternMapper {

    Intern toEntity(InternDto dto);
    InternDto toDto(Intern entity);

}
