package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDisableDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetWithOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;

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