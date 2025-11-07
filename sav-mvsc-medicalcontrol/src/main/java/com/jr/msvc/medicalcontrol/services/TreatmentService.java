package com.jr.msvc.medicalcontrol.services;

import java.util.List;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentResponseDto;

public interface TreatmentService {
    TreatmentResponseDto saveTreatment(TreatmentRequestDto treatmentDto);

    List<TreatmentResponseDto> findAlltreatments();
    
    TreatmentResponseDto findTreatmentByCode(String treatmentCode);
    
    List<TreatmentResponseDto> findTreatmentByPetCode(String petCode);

    List<TreatmentResponseDto> findTreatmentByConsultationCode(String consultation);
}
