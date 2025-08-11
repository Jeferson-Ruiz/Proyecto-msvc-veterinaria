package com.jr.sav_mvsc_medicalcontrol.mapper;

import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationReponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    
    Consultation toEntity(ConsultationDto dto);

    @Mapping(source = "pet.idPet", target = "idPet")
    ConsultationReponseDto toDto(Consultation entity);

}
