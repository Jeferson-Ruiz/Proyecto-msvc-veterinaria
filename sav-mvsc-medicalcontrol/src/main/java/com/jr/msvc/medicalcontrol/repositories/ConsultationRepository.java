package com.jr.msvc.medicalcontrol.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.msvc.medicalcontrol.models.AttendanceStatus;
import com.jr.msvc.medicalcontrol.models.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query("SELECT c FROM Consultation c WHERE c.consultationCode =:consultationCode")
    Optional<Consultation> findByConsultationCode(@Param("consultationCode") String consultationCode);

    Optional<Consultation> findFirstByPetPetCodeOrderByCitationDateDesc(String petCode);

    @Query("SELECT c FROM Consultation c WHERE c.pet.petCode =:petCode")
    List<Consultation> findAllByPetCode(@Param("petCode") String petCode);

    @Query("SELECT c FROM Consultation c WHERE c.status =:status")
    List<Consultation> findAllByStatus(@Param("status") AttendanceStatus status);

    @Query("SELECT c FROM Consultation c WHERE c.citationDate BETWEEN :start AND :end")
    List<Consultation> findByCitationDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT c FROM Consultation c WHERE c.vetCode =:vetCode")
    List<Consultation> findByVetCode(@Param("vetCode") String vetCode);

    @Query("SELECT COUNT(c) FROM Consultation c WHERE c.pet.petCode =:petCode")
    int countCitaByPetCode(@Param("petCode") String petCode);

    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
        FROM Consultation c
        WHERE c.vetCode =:vetCode
        AND c.citationDate BETWEEN :start AND :end """)
    boolean existsByVetAndTimeRange(@Param("vetCode") String vetCode, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}