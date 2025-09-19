package com.jr.sav_mvsc_medicalcontrol.mapper;

import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationsWithVetDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    
    Consultation toEntity(ConsultationRequestDto dto);

    @Mapping(source = "pet.idPet", target = "idPet")
    @Mapping(target = "fullName", ignore = true)
    ConsultationReponseDto toDto(Consultation entity);

    ConsultationsWithVetDto toVetDto(Consultation entity);

}
