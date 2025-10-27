package com.jeferson.msvc.workstaff.repositories;

import com.jeferson.msvc.workstaff.models.Administrative;
import com.jeferson.msvc.workstaff.models.AdministrativeRoles;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministrativeRepository extends JpaRepository<Administrative, Long> {

    @Query("SELECT a FROM Administrative a WHERE a.documentNumber =:documentNumber")
    Optional<Administrative> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    @Query("SELECT a FROM Administrative a WHERE a.status =:status")
    List<Administrative> findAllByStatus(@Param("status") EmployeeStatus status);

    @Query("SELECT a FROM Administrative a WHERE a.administrativeRoles =:administrativeRoles AND a.status =:status")
    List<Administrative> findAllByRoles(@Param("administrativeRoles") AdministrativeRoles administrativeRoles, @Param("status") EmployeeStatus status);

    Boolean existsByProfessionalCard(String professionalCard);

    @Modifying
    @Query("update Administrative set administrativeRoles=:administrativeRoles where employeeId =:employeeId")
    void updateRole(@Param ("employeeId") Long employeeId, @Param("administrativeRoles") AdministrativeRoles administrativeRoles);

}