package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;

public interface EmployeeService {

    List<EmployeeDto> findAll();

    Optional<EmployeeDto> findById(Long idEmployee);

    Optional<EmployeeDto> findByDocumentNumber(Long documentNumber);

    List<EmployeeDto> findAllByJobPosition(JobPosition jobPosition);

    void delete(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

}
