package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;

@Mapper(componentModel = "spring")
public interface AuxiliaryMapper {

    Auxiliary toEntity(AuxiliaryRequestDto dto);
    AuxiliaryRequestDto toDto(Auxiliary entity);

}
