package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jr.msvc.medicalcontrol.dto.vet.VetWithConsultationsDto;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationReponseDto;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.msvc.medicalcontrol.models.AttendanceStatus;

public interface ConsultationService {
    ConsultationReponseDto saveConsultation(ConsultationRequestDto consultationDto);
    
    List<ConsultationReponseDto> findAllConsultations();
    
    ConsultationReponseDto findConsultionByCode(String consultationCode);

    List<ConsultationReponseDto> findAllConsultationByPetCode(String petCode);

    List<ConsultationReponseDto> findAllByStatus(AttendanceStatus status);

    List<ConsultationReponseDto> findByDate(LocalDate date);

    VetWithConsultationsDto findConsultationsByVetCode(String vetCode);

    void updateConsultationDate(String consultatioCode, LocalDateTime newDate);

}