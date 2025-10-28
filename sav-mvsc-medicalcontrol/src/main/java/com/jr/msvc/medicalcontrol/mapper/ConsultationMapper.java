package com.jr.msvc.medicalcontrol.mapper;

import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationsWithVetDto;
import com.jr.msvc.medicalcontrol.models.Consultation;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationReponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsultationMapper {
    
    Consultation toEntity(ConsultationRequestDto dto);

    @Mapping(source = "pet.idPet", target = "idPet")
    @Mapping(target = "fullName", ignore = true)
    ConsultationReponseDto toDto(Consultation entity);

    @Mapping(source = "pet.idPet", target = "idPet")
    ConsultationsWithVetDto toVetDto(Consultation entity);

}
