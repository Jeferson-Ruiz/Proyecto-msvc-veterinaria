package com.jr.msvc.medicalcontrol.services;

import java.util.List;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerRequestDto;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerResponseDto;

public interface OwnerService {

    OwnerResponseDto saveOwner(OwnerRequestDto ownerDto);

    List<OwnerResponseDto> findAllByStatus(boolean status);

    void disableOwnerByDocument(String document) throws IllegalAccessException;

    OwnerResponseDto findOwnerByDocumentNumber(String documentNumber);

    void updatePhoneNumber(String document, String phoneNumber);

    void updateEmail(String document, String email);
    

}
