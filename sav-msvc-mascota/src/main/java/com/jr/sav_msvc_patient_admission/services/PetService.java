package com.jr.sav_msvc_patient_admission.services;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.dto.PetOwnerResponseDto;
import com.jr.sav_msvc_patient_admission.dto.PetResponseDto;

@RestController
public interface PetService {

    PetOwnerResponseDto savePet(PetDto petDto);

    List<PetResponseDto> findAllPets();

    List<PetOwnerResponseDto> findAllPetsWithOwners();

    PetResponseDto findPetById(Long idPet);

    PetOwnerResponseDto findByNameAndOwnerNumber(String name, Long ownerNumber);
    
    void deletePetById(Long idPet);

}