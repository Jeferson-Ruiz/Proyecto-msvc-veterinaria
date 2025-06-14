package com.jr.sav_msvc_patient_admission.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jr.sav_msvc_patient_admission.models.Pet;

public interface PetsRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByNameAndOwnerNumber(String name, Long owerId);

}
