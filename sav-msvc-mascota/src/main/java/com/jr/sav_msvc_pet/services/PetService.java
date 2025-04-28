package com.jr.sav_msvc_pet.services;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_msvc_pet.models.Pet;

@RestController
public interface PetService {

    Pet savePet(Pet pet);

    List<Pet> findAllPets();

    Optional<Pet> findPetById(Long idPet);

    Optional<Pet> findByNameAndOwerId(String name, Long owerId);

    void deletePetById(Long idPet);



}