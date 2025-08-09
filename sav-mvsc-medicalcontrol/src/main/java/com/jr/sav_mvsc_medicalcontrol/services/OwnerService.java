package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerDto;

public interface OwnerService {
    
    List<OwnerDto> findAllOwners();

    List<OwnerDto> findAllDisabeOwners();

    List<OwnerDto> findAllActiveOwners();

    OwnerDto findOwnerById(Long idOwner);

    OwnerDto findOwnerByDocumentNumber(Long documentNumber);

    OwnerDto saveOwner(OwnerDto ownerDto);

    void disableOwnerById(Long idOwner);
    
    void updatePhoneNumber(Long idOwner, Long phoneNumber);
    
    void updateEmail(Long idOwner, String email);

}
