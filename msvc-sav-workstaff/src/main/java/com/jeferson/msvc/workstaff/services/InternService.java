package com.jeferson.msvc.workstaff.services;

import java.util.List;
import com.jeferson.msvc.workstaff.dto.InternResponseDto;
import com.jeferson.msvc.workstaff.dto.InternRequestDto;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.InternRoles;

public interface InternService {

    InternResponseDto saveIntern(InternRequestDto internDto);

    List<InternResponseDto> findAllByStatus(EmployeeStatus status);
    
    List<InternResponseDto> findAllByRoleAndStatus(InternRoles internRole, EmployeeStatus status);
    
    InternResponseDto findAdminByDocumentNumber(String documentNumber);
    
    InternResponseDto findByCode(String employeeCode);

    void updateEmail(String employeeCode, String email);

    void updateNumberPhone(String employeeCode, String phoneNumber);

    void updateContractType(String employeeCode, ContractType contractType);

    void updateRoles(String employeeCode, InternRoles internRoles);

    void updateEmployeeStatus(String employeeCode, EmployeeStatus status);
    
    void suspended(String employeeCode, String deleteBy, String reason);
    
    void delete(String employeeCode, String deleteBy, String reason);
}