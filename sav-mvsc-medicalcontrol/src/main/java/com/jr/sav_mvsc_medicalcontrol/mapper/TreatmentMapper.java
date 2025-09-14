package com.jr.sav_mvsc_medicalcontrol.mapper;

import org.mapstruct.Mapper;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {
    Treatment toEntiry(TreatmentRequestDto dto);

    TreatmentResponseDto toDto(Treatment entity);

}
