package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import java.util.List;
import java.util.Optional;

public interface TreatmentService {
    List<Treatment> findAlltreatments();
    Optional<Treatment> findTreatmentById(Long idTreatment);
    Treatment saveTreatment(Treatment treatment);
    void deleteTreatment(Long idTreatment);
}
