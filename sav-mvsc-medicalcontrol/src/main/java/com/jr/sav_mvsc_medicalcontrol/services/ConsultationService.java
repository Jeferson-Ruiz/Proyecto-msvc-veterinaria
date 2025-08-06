package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;

public interface ConsultationService {

    List<ConsultationDto> findAllConsultations();

    ConsultationDto findConsultionById(Long idConsultation);

    ConsultationDto saveConsultation(ConsultationDto consultationDto);

    void deleteConsultation(Long idConsultation);

    ConsultationDto findConsultationByIdPet(Long idPet);

}