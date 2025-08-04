package com.jr.sav_msvc_patient_admission.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav_msvc_patient_admission.models.Pet;

public interface PetsRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.name =:name AND p.owner.documentNumber =:documentNumber")
    Optional<Pet> findByNameAndOwnerNumber(@Param("name") String name, @Param("documentNumber") Long documentNumber);

}
