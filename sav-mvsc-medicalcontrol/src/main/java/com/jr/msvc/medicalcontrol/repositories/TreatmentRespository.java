package com.jr.msvc.medicalcontrol.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.msvc.medicalcontrol.models.Treatment;

public interface TreatmentRespository extends JpaRepository<Treatment, Long> {

    @Query("SELECT t From Treatment t JOIN t.consultation c WHERE c.pet.petCode = :petCode")
    List<Treatment> findAllTreatmentsByPetCode(@Param("petCode") String petCode);

    @Query("SELECT t FROM Treatment t WHERE t.consultation.consultationCode =:consultationCode")
    List<Treatment> findAllTreatmentByIdcosultation(@Param("consultationCode") String consultationCode);

    @Query("SELECT COUNT(t) FROM Treatment t WHERE t.consultation.pet.petCode =:petCode")
    int countTreatmentByPetCode(@Param("petCode") String petCode);

    @Query("SELECT t FROM Treatment t WHERE t.treatmentCode =:treatmentCode")
    Optional<Treatment> findByTreatmentCode(@Param("treatmentCode") String treatmentCode);
}
