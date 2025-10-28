package com.jr.msvc.medicalcontrol.services;

import java.util.List;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerRequestDto;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerResponseDto;

public interface OwnerService {

    List<OwnerResponseDto> findAllDisabeOwners();

    List<OwnerResponseDto> findAllActiveOwners();

    OwnerResponseDto findOwnerById(Long idOwner);

    OwnerResponseDto findOwnerByDocumentNumber(String documentNumber);

    OwnerResponseDto saveOwner(OwnerRequestDto ownerDto);

    void disableOwnerById(Long idOwner) throws IllegalAccessException;
    
    void updatePhoneNumber(Long idOwner, String phoneNumber);
    
    void updateEmail(Long idOwner, String email);

}
