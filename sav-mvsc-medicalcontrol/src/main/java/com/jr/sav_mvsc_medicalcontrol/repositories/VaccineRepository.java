package com.jr.sav_mvsc_medicalcontrol.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    
    List<Vaccine> findByIdPet(Long petId);

}