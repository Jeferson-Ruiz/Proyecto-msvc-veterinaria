package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.models.AuxiliaryRoles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuxiliaryRepository  extends JpaRepository<Auxiliary, Long> {

    @Query("SELECT a FROM Auxiliary a WHERE a.documentNumber =:documentNumber")
    Optional<Auxiliary> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    @Query("SELECT a FROM Auxiliary a WHERE a.active")
    List<Auxiliary> findAllActiveAuxiliaries();

    @Modifying
    @Query("update Auxiliary set auxiliaryRoles=:auxiliaryRoles where employeeId =:employeeId")
    void updateRole(@Param ("employeeId") Long employeeId, @Param("auxiliaryRoles") AuxiliaryRoles auxiliaryRoles);
}
