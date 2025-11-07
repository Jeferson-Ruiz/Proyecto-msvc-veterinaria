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

    PetWithOwnerResponseDto findByNameAndOwnerNumber(String name, String ownerNumber);

    List<PetResponseDto> findPetsByOwnerDocument(String documentNumber);

    PetWithOwnerResponseDto findPetByCode(String petCode);

    List<PetWithOwnerResponseDto> findAllBySpecie(String specie);

    void disablePetByCode(String petCode, String deleteBy, String reason);
    
}