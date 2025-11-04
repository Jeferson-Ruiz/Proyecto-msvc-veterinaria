package com.jeferson.msvc.workstaff.repositories;

import com.jeferson.msvc.workstaff.models.Auxiliary;
import com.jeferson.msvc.workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuxiliaryRepository  extends JpaRepository<Auxiliary, Long> {

    @Query("SELECT a FROM Auxiliary a WHERE a.documentNumber =:documentNumber")
    Optional<Auxiliary> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    @Query("SELECT a FROM Auxiliary a WHERE a.employeeCode =:employeeCode")
    Optional<Auxiliary> findByCode(@Param("employeeCode") String employeeCode);

    @Query("SELECT a FROM Auxiliary a WHERE a.status =:status")
    List<Auxiliary> findAllByStatus(@Param("status") EmployeeStatus status);

    @Query("SELECT a FROM Auxiliary a WHERE a.auxiliaryRoles =:auxiliaryRole AND a.status =:status")
    List<Auxiliary> findByRoles(@Param("auxiliaryRole") AuxiliaryRoles auxiliaryRole, @Param("status") EmployeeStatus status);

    @Modifying
    @Query("update Auxiliary set auxiliaryRoles=:auxiliaryRoles where employeeCode =:employeeCode")
    void updateRole(@Param ("employeeCode") String employeeCode, @Param("auxiliaryRoles") AuxiliaryRoles auxiliaryRoles);
}
