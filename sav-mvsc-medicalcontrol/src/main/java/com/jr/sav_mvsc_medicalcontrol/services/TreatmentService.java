package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.dto.TreatmentDto;
import java.util.List;
import java.util.Optional;

public interface TreatmentService {
    List<TreatmentDto> findAlltreatments();

    Optional<TreatmentDto> findTreatmentById(Long idTreatment);

    Optional<TreatmentDto> saveTreatment(TreatmentDto treatmentDto);
    
    void deleteTreatment(Long idTreatment);
}
