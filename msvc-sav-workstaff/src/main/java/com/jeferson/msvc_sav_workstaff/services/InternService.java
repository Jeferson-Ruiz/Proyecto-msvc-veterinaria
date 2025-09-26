package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.InternResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
import com.jeferson.msvc_sav_workstaff.models.InternRoles;

public interface InternService {

    InternResponseDto saveIntern(InternRequestDto internDto);

    List<InternResponseDto> findAllByStatus(EmployeeStatus status);
    
    List<InternResponseDto> findAllByRole(InternRoles internRole, EmployeeStatus status);
    
    InternResponseDto findById(Long idEmployee);
    
    InternResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateRoles(Long idEmployee, InternRoles internRoles);

    void delete(Long idEmployee, String deleteBy, String reason);

    void suspended(Long idEmployee, String deleteBy, String reason);

    void updateEmployeeStatus(Long idEmployee, EmployeeStatus status);
}