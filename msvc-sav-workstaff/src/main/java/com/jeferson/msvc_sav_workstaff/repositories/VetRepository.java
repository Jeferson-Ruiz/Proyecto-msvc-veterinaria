package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.models.VetRoles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VetRepository extends JpaRepository<Vet, Long> {

    @Query("SELECT v FROM Vet v WHERE v.documentNumber =:documentNumber")
    Optional<Vet> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    Boolean existsByProfessionalCard(String professionalCard);

    @Query("SELECT v FROM Vet v WHERE v.status =:status")
    List<Vet> findAllActiveVets(@Param("status") EmployeeStatus status);

    @Query("SELECT v FROM Vet v WHERE v.vetRoles =:vetRole AND v.status =:status")
    List<Vet> findAllByRole(@Param("vetRole") VetRoles vetRole, @Param("status") EmployeeStatus status);

    @Modifying
    @Query("update Vet set vetRoles=:vetRoles where employeeId =:employeeId")
    void updateRole(@Param("employeeId") Long employeeId, @Param("vetRoles") VetRoles vetRoles);

}
