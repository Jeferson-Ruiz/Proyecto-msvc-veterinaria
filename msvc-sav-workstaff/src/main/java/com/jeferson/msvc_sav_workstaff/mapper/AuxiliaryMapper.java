package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;

@Mapper(componentModel = "spring")
public interface AuxiliaryMapper {

    Auxiliary toEntity(AuxiliaryDto dto);
    AuxiliaryDto toDto(Auxiliary entity);

}
