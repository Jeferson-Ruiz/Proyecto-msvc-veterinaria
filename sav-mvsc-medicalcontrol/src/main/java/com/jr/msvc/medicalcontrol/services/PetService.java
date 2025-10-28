package com.jr.msvc.medicalcontrol.services;

import java.util.List;
import com.jr.msvc.medicalcontrol.dto.pet.PetRequestDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetResponseDisableDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetResponseDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetWithOwnerResponseDto;


public interface PetService {

    PetWithOwnerResponseDto savePet(PetRequestDto petDto);

    List<PetResponseDisableDto> findAllDisablePets();

    List<PetWithOwnerResponseDto> findAllActivesPets();

    PetResponseDto findPetById(Long idPet);

    PetWithOwnerResponseDto findByNameAndOwnerNumber(String name, String ownerNumber);

    List<PetResponseDto> findPetsByOwnerDocument(String documentNumber);

    List<PetWithOwnerResponseDto> findAllBySpecie(String specie);

    void disablePetById(Long idPet, String deleteBy, String reason);
    
}