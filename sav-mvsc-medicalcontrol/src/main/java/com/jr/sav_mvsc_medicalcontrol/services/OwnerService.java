package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerResponseDto;

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
