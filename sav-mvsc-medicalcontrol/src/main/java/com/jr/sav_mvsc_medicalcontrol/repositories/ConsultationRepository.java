package com.jr.sav_mvsc_medicalcontrol.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;

public interface ConsultationRepository extends CrudRepository<Consultation, Long> {
    Optional<Consultation> findByIdPet(Long idPet);

}
