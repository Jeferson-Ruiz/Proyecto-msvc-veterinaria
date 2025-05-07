package com.jr.sav_mvsc_medicalcontrol.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;

public interface ConsultationRepository extends CrudRepository<Consultation, Long> {
}
