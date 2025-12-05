package com.jeferson.msvc.workstaff.services;

import java.util.List;
import java.util.Set;
import com.jeferson.msvc.workstaff.dto.EmployeeRequestDto;
import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.Role;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.WorkArea;

public interface EmployeeService {

    EmployeeResponseDto save(EmployeeRequestDto employeeDto);

    EmployeeResponseDto findByEmployeeCode(String code);

    EmployeeResponseDto findByDocumentNumber(String documentNumber);

    List<EmployeeResponseDto> findAllByAreaRolAndStatus(WorkArea area, String role, EmployeeStatus status);

    void updateEmail(String employeeCode, String newEmail);

    void updatePhone(String employeeCode, String newPhone);

    void updateRole(String employeeCode, Set<Role> role);

    void updateContractType(String employeeCode ,ContractType newContract);

    void delete(String employeeCode, String deleteBy, String reason);

    void updateEmployeeStatus(String employeeCode, EmployeeStatus employeeStatus);


}