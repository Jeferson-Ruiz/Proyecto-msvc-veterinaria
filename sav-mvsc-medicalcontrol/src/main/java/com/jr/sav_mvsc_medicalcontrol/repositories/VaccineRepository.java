package com.jr.sav_mvsc_medicalcontrol.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;

public interface VaccineRepository extends CrudRepository<Vaccine, Long> {

}