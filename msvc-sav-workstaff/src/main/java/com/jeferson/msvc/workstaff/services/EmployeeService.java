package com.jeferson.msvc.workstaff.services;

import java.util.List;
import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.WorkArea;

public interface EmployeeService {

    List<EmployeeResponseDto> findAllByStatus(EmployeeStatus status);

    List<EmployeeResponseDto> getEmployeesByType(WorkArea workArea, EmployeeStatus status);

    EmployeeResponseDto findById(Long idEmployee);

    EmployeeResponseDto findByDocumentNumber(String documentNumber);

    void delete(Long idEmployee, String deleteBy, String reason);

    void updateEmployeeStatus(Long idEmployee, EmployeeStatus employeeStatus);

}