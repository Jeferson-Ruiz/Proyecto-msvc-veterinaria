package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jr.msvc.medicalcontrol.dto.vet.VetWithConsultationsDto;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationReponseDto;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.msvc.medicalcontrol.models.AttendanceStatus;

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