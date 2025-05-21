package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;

public interface EmployeeService {

    List<Employee> findAll();

    Optional<Employee> findEmployeeById(Long idEmployee);

    List<Employee> findAllByJobPosition(JobPosition jobPosition);

    void delete(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

}
