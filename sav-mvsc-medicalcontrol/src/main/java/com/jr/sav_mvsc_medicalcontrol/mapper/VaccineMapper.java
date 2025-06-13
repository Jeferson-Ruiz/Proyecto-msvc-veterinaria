package com.jr.sav_mvsc_medicalcontrol.mapper;

import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VaccineMapper {
    Vaccine toEntity (VaccineDto dto);
    VaccineDto toDto(Vaccine toEntity);
}
