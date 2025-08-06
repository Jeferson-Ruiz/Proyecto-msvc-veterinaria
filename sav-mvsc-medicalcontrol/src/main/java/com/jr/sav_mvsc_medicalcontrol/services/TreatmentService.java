package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.dto.TreatmentDto;
import java.util.List;

public interface TreatmentService {
    List<TreatmentDto> findAlltreatments();

    List<TreatmentDto> findTreatmentByIdPet(Long idPet);

    TreatmentDto findTreatmentById(Long idTreatment);

    TreatmentDto saveTreatment(TreatmentDto treatmentDto);
    
}
