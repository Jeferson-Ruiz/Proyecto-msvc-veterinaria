package com.jr.sav_mvsc_medicalcontrol.mapper;

import com.jr.sav_mvsc_medicalcontrol.dto.VaccineRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineResponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VaccineMapper {
    Vaccine toEntity (VaccineRequestDto dto);

    @Mapping(source = "pet.idPet", target = "idPet")
    VaccineResponseDto toDto(Vaccine toEntity);
}
