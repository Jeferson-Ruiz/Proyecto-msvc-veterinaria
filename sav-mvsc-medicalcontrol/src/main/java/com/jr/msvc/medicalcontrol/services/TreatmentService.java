package com.jr.msvc.medicalcontrol.services;

import java.util.List;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentResponseDto;

public interface TreatmentService {
    List<TreatmentResponseDto> findAlltreatments();
    
    TreatmentResponseDto findTreatmentById(Long idTreatment);
    
    TreatmentResponseDto saveTreatment(TreatmentRequestDto treatmentDto);

    List<TreatmentResponseDto> findTreatmentByIdPet(Long idPet);

    List<TreatmentResponseDto> findTreatmentByIdConsultation(Long idPet);
}
