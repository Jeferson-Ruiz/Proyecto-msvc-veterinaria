package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import java.util.Optional;
import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;

public interface ConsultationService {

    List<ConsultationDto> findAllConsultations();

    Optional<ConsultationDto> finConsultionById(Long idConsultation);

    Optional<ConsultationDto> saveConsultation(ConsultationDto consultationDto);

    void deleteConsultation(Long idConsultation);
    
    Optional<ConsultationDto> findConsultationByIdPet(Long idPet);

}