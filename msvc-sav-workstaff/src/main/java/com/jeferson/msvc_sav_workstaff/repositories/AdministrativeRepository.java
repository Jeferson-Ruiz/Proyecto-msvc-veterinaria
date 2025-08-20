package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Administrative;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministrativeRepository extends JpaRepository<Administrative, Long> {

    @Modifying
    @Query("update Administrative set workArea=:workArea where idEmployee=:idEmployee")
    void updateWorkArea(@Param("idEmployee") Long idEmployee, @Param("workArea") String workArea);

    @Query("SELECT a FROM Administrative a WHERE a.documentNumber =:documentNumber")
    Optional<Administrative> findByDocumentNumber(@Param("documentNumber") String documentNumber);

}
