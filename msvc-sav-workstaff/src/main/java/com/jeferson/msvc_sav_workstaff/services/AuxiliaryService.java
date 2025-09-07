package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc_sav_workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface AuxiliaryService {

    AuxiliaryResponseDto saveAuxiliary(AuxiliaryRequestDto auxiliaryDto);

    List<AuxiliaryResponseDto> findAllAuxiliary();

    List<AuxiliaryResponseDto> findAllDisabledAuxiliary();

    List<AuxiliaryResponseDto> findAllByRoles(AuxiliaryRoles auxiliaryRole);

    AuxiliaryResponseDto findById(Long idEmployee);

    AuxiliaryResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updatePhoneNumber(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateRole(Long idEmployee, AuxiliaryRoles auxiliaryRole);

    void delete (Long idEmployee);

}
