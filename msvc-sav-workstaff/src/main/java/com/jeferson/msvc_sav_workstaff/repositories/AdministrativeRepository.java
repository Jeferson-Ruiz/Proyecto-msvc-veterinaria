package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Administrative;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdministrativeRepository extends CrudRepository<Administrative, Long> {

    @Modifying
    @Query("update Administrative set workArea=:workArea where idEmployee=:idEmployee")
    void updateWorkArea(@Param("idEmployee") Long idEmployee, @Param("workArea") String workArea);

}
