package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface AuxiliaryService {

    Optional<AuxiliaryDto> saveAuxiliary(AuxiliaryDto auxiliaryDto);

    Optional<AuxiliaryDto> findById(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updatePhoneNumber(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateWorkStatus(Long idEmployee, Boolean workStatus);

    void delete (Long idEmployee);

}
