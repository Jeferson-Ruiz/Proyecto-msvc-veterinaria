package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Intern;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InternRepository extends JpaRepository<Intern, Long> {
    @Query("SELECT a FROM a Intern WHERE a.documentNumber =:documentNumber")
    Optional<Intern> findByDocumentNumber(@Param("documentNumber") String documentNumber);

}