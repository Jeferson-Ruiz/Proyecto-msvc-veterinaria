package com.jr.sav_mvsc_medicalcontrol.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;

public interface TreatmentRespository extends CrudRepository<Treatment, Long> {

}
