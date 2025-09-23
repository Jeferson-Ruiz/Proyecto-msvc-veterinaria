package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.AdministrativeRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;

public interface AdministrativeService {

    AdmistrativeResponseDto saveAdministrative(AdministrativeRequestDto administrative);

    List<?> findAllByStatus(EmployeeStatus status);

    List<?> findAllByRole(AdministrativeRoles administrativeRole, EmployeeStatus status);
    
    AdmistrativeResponseDto findAdminById (Long idEmployee);
    
    AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);
    
    void updateRole(Long idEmployee, AdministrativeRoles admiRoles);

    void delete (Long idEmployee, String deleteAt, String reason);

    void suspended(Long idEmployee, String deleteAt, String reason);

}
