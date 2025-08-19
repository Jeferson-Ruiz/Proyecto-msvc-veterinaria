package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.VetResponseDto;
import com.jeferson.msvc_sav_workstaff.models.Vet;

@Mapper(componentModel = "spring")
public interface VetMapper {

    Vet toEntity(VetRequestDto dto);
    VetResponseDto toDto(Vet entity);

}
