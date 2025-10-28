package com.jr.msvc.medicalcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineRequestDto;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineResponseDto;
import com.jr.msvc.medicalcontrol.models.Vaccine;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VaccineMapper {
    Vaccine toEntity (VaccineRequestDto dto);

    @Mapping(source = "pet.idPet", target = "idPet")
    VaccineResponseDto toDto(Vaccine toEntity);
}
