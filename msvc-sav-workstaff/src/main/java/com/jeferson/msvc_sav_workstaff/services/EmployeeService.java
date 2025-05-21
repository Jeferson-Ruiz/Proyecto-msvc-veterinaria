package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;

public interface EmployeeService {

    List<Employee> findAllEmployee();

    Optional<Employee> findEmployeeById(Long idEmployee);

    List<Employee> FindAllByJobPosition(JobPosition jobPosition);

    Employee saveEmployee(Employee employee);

    void deleteEmployee(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

}
