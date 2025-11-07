package com.jr.msvc.medicalcontrol.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.msvc.medicalcontrol.models.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    @Query("SELECT v FROM Vaccine v Where v.pet.petCode =:petCode")
    List<Vaccine> findAllVaccinesByPetCode(@Param("petCode") String petCode);

    @Query("SELECT v FROM Vaccine v WHERE v.vaccineCode=:vaccineCode")
    Optional<Vaccine> findVaccineBycode(@Param("vaccineCode") String vaccineCode);

}