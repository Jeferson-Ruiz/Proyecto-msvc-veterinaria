package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDateTime;
import java.util.List;

import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;

public interface ConsultationService {

    List<ConsultationReponseDto> findAllConsultations();

    ConsultationReponseDto findConsultionById(Long idConsultation);

    ConsultationReponseDto saveConsultation(ConsultationRequestDto consultationDto);

    List<ConsultationReponseDto> findAllConsultationByIdPet(Long idPet);

    void updateConsultationDate(Long idConsultation, LocalDateTime newDate);

}