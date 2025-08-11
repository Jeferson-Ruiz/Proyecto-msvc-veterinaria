package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentResponseDto;

public interface TreatmentService {
    List<TreatmentResponseDto> findAlltreatments();
    
    TreatmentResponseDto findTreatmentById(Long idTreatment);
    
    TreatmentResponseDto saveTreatment(TreatmentDto treatmentDto);

    List<TreatmentResponseDto> findTreatmentByIdPet(Long idPet);

    List<TreatmentResponseDto> findTreatmentByIdConsultation(Long idPet);
}
