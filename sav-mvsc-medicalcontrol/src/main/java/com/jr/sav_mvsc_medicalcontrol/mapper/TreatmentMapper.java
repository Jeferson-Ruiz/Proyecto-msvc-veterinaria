package com.jr.sav_mvsc_medicalcontrol.mapper;

import com.jr.sav_mvsc_medicalcontrol.dto.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {
    Treatment toEntiry(TreatmentDto dto);
    TreatmentDto toDto(Treatment entity);

}
