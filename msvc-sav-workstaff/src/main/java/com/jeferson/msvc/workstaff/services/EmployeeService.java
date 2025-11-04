package com.jeferson.msvc.workstaff.services;

import java.util.List;
import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.WorkArea;

public interface EmployeeService {

    List<EmployeeResponseDto> findAllByStatus(EmployeeStatus status);

    List<EmployeeResponseDto> getEmployeesByType(WorkArea workArea, EmployeeStatus status);

    EmployeeResponseDto findByEmployeeCode(String code);

    EmployeeResponseDto findByDocumentNumber(String documentNumber);

    void delete(String employeeCode, String deleteBy, String reason);

    void updateEmployeeStatus(String employeeCode, EmployeeStatus employeeStatus);

}