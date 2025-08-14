package com.jr.sav_mvsc_medicalcontrol.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    @Query("SELECT v FROM Vaccine v Where v.pet.idPet =:petId")
    List<Vaccine> findAllByIdPet(@Param("petId") Long petId);

    @Modifying    
    @Query("UPDATE Vaccine v SET v.nextApplicationDate =:nextApplicationDate WHERE v.idVaccine =:idVaccine")
    void updateNextApplicationDate(@Param("idVaccine") Long idVaccine, @Param("nextApplicationDate") LocalDate nextApplicationDate);

    @Modifying
    @Query("UPDATE Vaccine v SET v.name =:name WHERE v.idVaccine =:idVaccine")
    void updateName(@Param("idVaccine") Long idVaccine, @Param("name") String name);

}