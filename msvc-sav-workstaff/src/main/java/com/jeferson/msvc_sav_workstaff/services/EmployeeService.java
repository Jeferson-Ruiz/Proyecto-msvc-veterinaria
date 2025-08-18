package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;

public interface EmployeeService {

    List<EmployeeResponseDto> findAll();

    EmployeeResponseDto findById(Long idEmployee);

    EmployeeResponseDto findByDocumentNumber(String documentNumber);

    List<EmployeeResponseDto> findAllByJobPosition(JobPosition jobPosition);

    void delete(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);
    
}
