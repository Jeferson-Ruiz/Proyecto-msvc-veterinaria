package com.jr.sav_mvsc_medicalcontrol.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findByIdPet(Long idPet);

    List<Consultation> findAllByIdPet(Long idPet);
}
