package com.jeferson.msvc.workstaff.services;

import java.util.List;
import com.jeferson.msvc.workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc.workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc.workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;

public interface AuxiliaryService {

    AuxiliaryResponseDto saveAuxiliary(AuxiliaryRequestDto auxiliaryDto);

    List<AuxiliaryResponseDto> findAllByStatus(EmployeeStatus status);

    List<AuxiliaryResponseDto> findAllByRoles(AuxiliaryRoles auxiliaryRole, EmployeeStatus status);

    AuxiliaryResponseDto findAdminByDocumentNumber(String documentNumber);

    AuxiliaryResponseDto findAdminByCode(String employeeCode);

    void updateEmail(String employeeCode, String email);

    void updatePhoneNumber(String employeeCode, String phoneNumber);

    void updateContractType(String employeeCode, ContractType contractType);

    void updateRole(String employeeCode, AuxiliaryRoles auxiliaryRole);

    void updateEmployeeStatus(String employeeCode, EmployeeStatus employeeStatus);

    void suspended(String employeeCode, String deleteBy, String reason);
    
    void delete(String employeeCode, String deleteBy, String reason);

}
