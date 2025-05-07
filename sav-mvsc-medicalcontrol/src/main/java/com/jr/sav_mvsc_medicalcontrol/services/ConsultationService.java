package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import java.util.Optional;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;

public interface ConsultationService {

    List<Consultation> findAllConsultations();

    Optional<Consultation> finConsultionById(Long idConsultation);

    Consultation saveConsultation(Consultation consultation);

    void deleteConsultation(Long idConsultation);

}