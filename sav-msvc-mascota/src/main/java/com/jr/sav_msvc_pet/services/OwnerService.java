package com.jr.sav_msvc_pet.services;

import java.util.List;
import java.util.Optional;
import com.jr.sav_msvc_pet.dto.OwnerDto;
import com.jr.sav_msvc_pet.models.Owner;

public interface OwnerService {
    List<OwnerDto> findAllOwners();

    Optional<OwnerDto> findOwnerById(Long idOwner);

    Optional<OwnerDto> findOwnerByDocumentNumber(Long documentNumber);

    Optional<Owner> saveOwner(OwnerDto ownerDto);

    void deleteOwnerById(Long idOwner);

    void updatePhoneNumber(Long idOwner, Long phoneNumber);
    
    void updateEmail(Long idOwner, String email);
}