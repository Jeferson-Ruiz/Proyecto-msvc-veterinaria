package com.jr.msvc.medicalcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.msvc.medicalcontrol.models.Treatment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TreatmentMapper {
    Treatment toEntiry(TreatmentRequestDto dto);

    TreatmentResponseDto toDto(Treatment entity);

}
