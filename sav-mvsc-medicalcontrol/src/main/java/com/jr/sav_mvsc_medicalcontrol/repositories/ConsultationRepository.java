package com.jr.sav_mvsc_medicalcontrol.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findFirstByPetIdPetOrderByCitationDateDesc(Long idPet);

    @Query("SELECT c FROM Consultation c WHERE c.pet.idPet =:idPet")
    List<Consultation> findAllByIdPet(@Param("idPet") Long idPet);

    @Modifying
    @Query("UPDATE Consultation c SET c.citationDate = :citationDate WHERE c.idConsultation =:idConsultation")
    void updateCitationDate(@Param("idConsultation") Long idConsultation,@Param("citationDate") LocalDateTime citationDate);

}
