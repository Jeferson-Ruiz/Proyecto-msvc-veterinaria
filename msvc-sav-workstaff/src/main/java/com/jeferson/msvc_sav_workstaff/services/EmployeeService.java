package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;

public interface EmployeeService {

    List<EmployeeResponseDto> findAllByStatus(EmployeeStatus status);

    List<EmployeeResponseDto> getEmployeesByType(WorkArea workArea, EmployeeStatus status);

    EmployeeResponseDto findById(Long idEmployee);

    EmployeeResponseDto findByDocumentNumber(String documentNumber);

    void delete(Long idEmployee, String deleteBy, String reason);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    EmployeeStatus validateStatus(EmployeeStatus status);

}