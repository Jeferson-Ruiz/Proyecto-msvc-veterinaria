package com.jr.sav_msvc_patient_admission.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.jr.sav_msvc_patient_admission.models.Pet;

public interface PetsRepository extends CrudRepository<Pet, Long>{
    Optional<Pet> findByNameAndOwerId(String name, Long owerId);

}
