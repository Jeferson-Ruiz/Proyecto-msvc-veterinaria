package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.models.AdministrativeRoles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministrativeRepository extends JpaRepository<Administrative, Long> {

    @Query("SELECT a FROM Administrative a WHERE a.documentNumber =:documentNumber")
    Optional<Administrative> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    @Query("SELECT a FROM Administrative a WHERE a.active")
    List<Administrative> findAllActiveAdministrators();

    @Query("SELECT a FROM Administrative a WHERE a.active = false")
    List<Administrative> findAllDisabledAdministrators();

    Boolean existsByProfessionalCard(String professionalCard);

    @Modifying
    @Query("update Administrative set administrativeRoles=:administrativeRoles where employeeId =:employeeId")
    void updateRole(@Param ("employeeId") Long employeeId, @Param("administrativeRoles") AdministrativeRoles administrativeRoles);

}