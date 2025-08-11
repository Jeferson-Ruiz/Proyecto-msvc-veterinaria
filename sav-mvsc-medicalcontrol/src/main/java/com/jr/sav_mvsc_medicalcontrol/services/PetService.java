package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;

import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;

public interface PetService {

    PetOwnerResponseDto savePet(PetDto petDto);

    List<PetResponseDto> findAllPets();

    List<PetOwnerResponseDto> findAllDisablePets();

    List<PetOwnerResponseDto> findAllActivesPets();

    PetResponseDto findPetById(Long idPet);

    PetOwnerResponseDto findByNameAndOwnerNumber(String name, Long ownerNumber);

    List<PetResponseDto> findPetsByOwner(Long documentNumber);

    void disablePetById(Long idPet);
    
}