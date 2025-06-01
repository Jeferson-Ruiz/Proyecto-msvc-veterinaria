package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface AuxiliaryService {

    Optional<Auxiliary> saveAuxiliary(AuxiliaryDto auxiliaryDto);

    Optional<AuxiliaryDto> findById(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updatePhoneNumber(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void delete (Long idEmployee);

}
