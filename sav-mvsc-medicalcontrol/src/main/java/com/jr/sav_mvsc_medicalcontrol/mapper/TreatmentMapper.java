package com.jr.sav_mvsc_medicalcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TreatmentMapper {
    Treatment toEntiry(TreatmentRequestDto dto);

    TreatmentResponseDto toDto(Treatment entity);

}
