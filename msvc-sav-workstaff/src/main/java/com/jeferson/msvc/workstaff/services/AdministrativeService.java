package com.jeferson.msvc.workstaff.services;

import java.util.List;
import com.jeferson.msvc.workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc.workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc.workstaff.models.AdministrativeRoles;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;

public interface AdministrativeService {

    AdmistrativeResponseDto saveAdministrative(AdministrativeRequestDto administrative);

    List<AdmistrativeResponseDto> findAllByStatus(EmployeeStatus status);

    List<AdmistrativeResponseDto> findAllByRole(AdministrativeRoles administrativeRole, EmployeeStatus status);
        
    AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber);

    AdmistrativeResponseDto findAdminByCode(String employeeCode);

    void updateEmail(String employeeCode, String email);

    void updateNumberPhone(String employeeCode, String phoneNumber);

    void updateContractType(String employeeCode, ContractType contractType);
    
    void updateRole(String employeeCode, AdministrativeRoles admiRoles);

    void updateEmployeeStatus(String employeeCode, EmployeeStatus employeeStatus);

    void suspended(String employeeCode, String deleteAt, String reason);

    void delete (String employeeCode, String deleteAt, String reason);


}
