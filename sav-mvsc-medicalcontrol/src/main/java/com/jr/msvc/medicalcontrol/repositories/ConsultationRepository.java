package com.jr.msvc.medicalcontrol.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.msvc.medicalcontrol.models.AttendanceStatus;
import com.jr.msvc.medicalcontrol.models.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findFirstByPetIdPetOrderByCitationDateDesc(Long idPet);

    @Query("SELECT c FROM Consultation c WHERE c.pet.idPet =:idPet")
    List<Consultation> findAllByIdPet(@Param("idPet") Long idPet);

    @Modifying
    @Query("UPDATE Consultation c SET c.citationDate = :citationDate WHERE c.idConsultation =:idConsultation")
    void updateCitationDate(@Param("idConsultation") Long idConsultation,@Param("citationDate") LocalDateTime citationDate);

    @Query("SELECT c FROM Consultation c WHERE c.status =:status")
    List<Consultation> findAllByStatus(@Param("status") AttendanceStatus status);

    @Query("SELECT c FROM Consultation c WHERE c.citationDate BETWEEN :start AND :end")
    List<Consultation> findByCitationDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT c FROM Consultation c WHERE c.vetId =:vetId" )
    List<Consultation> findByVetId(@Param("vetId") Long vetId);

    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
        FROM Consultation c
        WHERE c.vetId = :vetId
        AND c.citationDate BETWEEN :start AND :end """)
    boolean existsByVetAndTimeRange(@Param("vetId") Long vetId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
