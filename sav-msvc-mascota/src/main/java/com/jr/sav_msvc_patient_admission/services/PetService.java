package com.jr.sav_msvc_patient_admission.services;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.models.Pet;

@RestController
public interface PetService {

    Optional<Pet> savePet(PetDto petDto);

    List<PetDto> findAllPets();

    Optional<PetDto> findPetById(Long idPet);

    Optional<PetDto> findByNameAndOwnerNumber(String name, Long ownerNumber);
    
    void deletePetById(Long idPet);

}