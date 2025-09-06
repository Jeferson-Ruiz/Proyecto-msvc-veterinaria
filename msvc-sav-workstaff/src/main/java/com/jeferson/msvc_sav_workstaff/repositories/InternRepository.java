package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.models.InternRoles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InternRepository extends JpaRepository<Intern, Long> {
    @Query("SELECT i FROM Intern i WHERE i.documentNumber =:documentNumber")
    Optional<Intern> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    @Query("SELECT i FROM Intern i WHERE i.active")
    List<Intern> findAllActiveInterns();

    @Query("SELECT i FROM Intern i WHERE i.active =false")
    List<Intern> findAllDisabledInterns();

    @Modifying
    @Query("update Intern set internRoles=:internRoles where employeeId =:employeeId")
    void updateRole(@Param("employeeId") Long employeeId, @Param("internRoles") InternRoles internRoles);

}