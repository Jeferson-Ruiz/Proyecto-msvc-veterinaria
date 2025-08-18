package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface AuxiliaryService {

    AuxiliaryRequestDto saveAuxiliary(AuxiliaryRequestDto auxiliaryDto);

    AuxiliaryRequestDto findById(Long idEmployee);

    AuxiliaryRequestDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updatePhoneNumber(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void delete (Long idEmployee);

}
