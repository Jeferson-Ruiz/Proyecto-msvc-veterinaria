package com.jr.sav_mvsc_medicalcontrol.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;

public interface TreatmentRespository extends JpaRepository<Treatment, Long> {

    @Query("SELECT t From Treatment t JOIN t.consultation c WHERE c.pet.idPet = :idPet")
    List<Treatment> findAllTreatmentsByIdpet(@Param("idPet") Long idPet);

    @Query("SELECT t FROM Treatment t WHERE t.consultation.idConsultation =:idConsultation")
    List<Treatment> findAllTreatmentByIdcosultation(@Param("idConsultation") Long idConsultation);

}
