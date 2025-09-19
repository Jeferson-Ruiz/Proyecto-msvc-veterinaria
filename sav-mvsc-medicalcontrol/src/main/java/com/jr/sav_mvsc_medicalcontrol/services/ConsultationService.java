package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.sav_mvsc_medicalcontrol.models.AttendanceStatus;
import com.jr.sav_mvsc_medicalcontrol.dto.VetWithConsultationsDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;

public interface ConsultationService {

    List<ConsultationReponseDto> findAllConsultations();

    ConsultationReponseDto findConsultionById(Long idConsultation);

    ConsultationReponseDto saveConsultation(ConsultationRequestDto consultationDto);

    List<ConsultationReponseDto> findAllConsultationByIdPet(Long idPet);

    List<ConsultationReponseDto> findAllByStatus(AttendanceStatus status);

    List<ConsultationReponseDto> findByDate(LocalDate date);

    VetWithConsultationsDto findConsultationsByIdVet(Long vetId);

    void updateConsultationDate(Long idConsultation, LocalDateTime newDate);

}