package com.jr.sav_mvsc_medicalcontrol.mapper;

import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineResponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaccineMapper {
    Vaccine toEntity (VaccineDto dto);

    @Mapping(source = "pet.idPet", target = "idPet")
    VaccineResponseDto toDto(Vaccine toEntity);
}
