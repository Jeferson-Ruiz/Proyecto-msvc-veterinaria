package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;

public interface EmployeeService {

    List<EmployeeResponseDto> findAll();

    List<EmployeeResponseDto> getEmployeesByType(WorkArea workArea);

    EmployeeResponseDto findById(Long idEmployee);

    EmployeeResponseDto findByDocumentNumber(String documentNumber);

    void delete(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

}