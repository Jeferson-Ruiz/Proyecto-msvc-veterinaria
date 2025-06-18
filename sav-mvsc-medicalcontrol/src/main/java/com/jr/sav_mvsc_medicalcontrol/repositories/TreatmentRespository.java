package com.jr.sav_mvsc_medicalcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;

public interface TreatmentRespository extends JpaRepository<Treatment, Long> {
    

}
