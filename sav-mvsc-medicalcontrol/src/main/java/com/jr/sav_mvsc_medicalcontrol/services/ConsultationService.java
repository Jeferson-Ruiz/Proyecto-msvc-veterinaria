package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDateTime;
import java.util.List;

import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;

public interface ConsultationService {

    List<ConsultationReponseDto> findAllConsultations();

    ConsultationReponseDto findConsultionById(Long idConsultation);

    ConsultationReponseDto saveConsultation(ConsultationDto consultationDto);

    ConsultationReponseDto findConsultationByIdPet(Long idPet);

    List<ConsultationReponseDto> findAllConsultationById(Long idPet);

    void updateConsultationDate(Long idConsultation, LocalDateTime newDate);

}