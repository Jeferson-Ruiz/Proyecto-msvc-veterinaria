package com.jeferson.msvc.workstaff.services;

import org.springframework.stereotype.Service;
import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;

@Service
public abstract class CodeEmployeeService {

    protected EmployeeRepository employeeRepo;

    public CodeEmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    protected String generateEmployeeCode(String prefix, Employee employee) {
        String lastDigits = employee.getDocumentNumber()
                .substring(employee.getDocumentNumber().length() - 4);
        Long count = employeeRepo.count() + 1;

        return String.format("%s%s-%02d", prefix, lastDigits, count);
    }

}
