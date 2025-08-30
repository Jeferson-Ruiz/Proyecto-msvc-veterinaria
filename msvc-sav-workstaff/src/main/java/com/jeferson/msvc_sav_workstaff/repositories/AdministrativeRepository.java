package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Administrative;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministrativeRepository extends JpaRepository<Administrative, Long> {

    @Query("SELECT a FROM Administrative a WHERE a.documentNumber =:documentNumber")
    Optional<Administrative> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    Boolean existsByProfessionalCard(String professionalCard);
}