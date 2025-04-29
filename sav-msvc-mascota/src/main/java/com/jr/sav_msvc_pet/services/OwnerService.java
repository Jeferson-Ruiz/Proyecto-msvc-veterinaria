package com.jr.sav_msvc_pet.services;

import java.util.List;
import java.util.Optional;
import com.jr.sav_msvc_pet.models.Owner;

public interface OwnerService {
    List<Owner> findAllOwners();

    Optional<Owner> findOwnerById(Long idOwner);

    Optional<Owner> findOwnerByDocumentNumber(Long documentNumber);

    Owner saveOwner(Owner owner);

    void deleteOwnerById(Long idOwner);

    void updatePhoneNumber(Long idOwner, Long phoneNumber);
    
    void updateEmail(Long idOwner, String email);
}