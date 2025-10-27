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
    
    List<VetResponseDto> findAllByRole(VetRoles vetRole, EmployeeStatus status);

    VetResponseDto findById(Long idEmployee);

    VetResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateRole(Long idEmployee, VetRoles vetRoles);

    void delete(Long idEmployee, String deleteBy, String reason);

    void suspended(Long idEmployee, String deleteBy, String reason);

    void updateEmployeeStatus(Long idEmployee, EmployeeStatus status);
}