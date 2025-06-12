package com.jr.sav_msvc_patient_admission.services;

import java.util.List;
import java.util.Optional;

import com.jr.sav_msvc_patient_admission.dto.OwnerDto;
import com.jr.sav_msvc_patient_admission.models.Owner;

public interface OwnerService {
    List<OwnerDto> findAllOwners();

    Optional<OwnerDto> findOwnerById(Long idOwner);

    Optional<OwnerDto> findOwnerByDocumentNumber(Long documentNumber);

    Optional<Owner> saveOwner(OwnerDto ownerDto);

    void deleteOwnerById(Long idOwner);

    void updatePhoneNumber(Long idOwner, Long phoneNumber);
    
    void updateEmail(Long idOwner, String email);
}