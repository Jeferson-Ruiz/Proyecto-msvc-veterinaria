package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;

import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetWithOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;

public interface PetService {

    PetWithOwnerResponseDto savePet(PetRequestDto petDto);

    List<PetResponseDto> findAllPets();

    List<PetWithOwnerResponseDto> findAllDisablePets();

    List<PetWithOwnerResponseDto> findAllActivesPets();

    PetResponseDto findPetById(Long idPet);

    PetWithOwnerResponseDto findByNameAndOwnerNumber(String name, Long ownerNumber);

    List<PetResponseDto> findPetsByOwner(Long documentNumber);

    void disablePetById(Long idPet);
    
}