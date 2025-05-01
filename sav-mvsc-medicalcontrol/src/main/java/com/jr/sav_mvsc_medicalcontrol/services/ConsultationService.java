package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;

public interface ConsultationService {

    List<Consultation> findAllConsultations();

}
