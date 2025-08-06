package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.PetDto;
import com.jr.sav_mvsc_medicalcontrol.dto.PetOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.PetResponseDto;

public interface PetService {

    PetOwnerResponseDto savePet(PetDto petDto);

    List<PetResponseDto> findAllPets();

    List<PetOwnerResponseDto> findAllPetsWithOwners();

    PetResponseDto findPetById(Long idPet);

    PetOwnerResponseDto findByNameAndOwnerNumber(String name, Long ownerNumber);

    List<PetResponseDto> findPetsByOwner(Long documentNumber);
    
    void deletePetById(Long idPet);

}