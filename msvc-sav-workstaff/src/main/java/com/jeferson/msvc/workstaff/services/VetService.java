package com.jeferson.msvc.workstaff.services;

import java.util.List;
import com.jeferson.msvc.workstaff.dto.VetRequestDto;
import com.jeferson.msvc.workstaff.dto.VetResponseDto;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.VetRoles;

public interface VetService {

    VetResponseDto saveVet(VetRequestDto vetDto);

    List<VetResponseDto> findAllByStatus(EmployeeStatus status);
    
    List<VetResponseDto> findAllByRoleAndStatus(VetRoles vetRole, EmployeeStatus status);

    VetResponseDto findByCode(String employeeCode);

    VetResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(String employeeCode, String email);

    void updateNumberPhone(String employeeCode, String phoneNumber);

    void updateContractType(String employeeCode, ContractType contractType);

    void updateRole(String employeeCode, VetRoles vetRoles);

    void updateEmployeeStatus(String employeeCode, EmployeeStatus status);
    
    void suspended(String employeeCode, String deleteBy, String reason);
    
    void delete(String employeeCode, String deleteBy, String reason);
}