package com.jeferson.msvc.workstaff.services;

import org.springframework.stereotype.Component;
import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.models.Role;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;
import com.jeferson.msvc.workstaff.repositories.RoleRepository;

@Component
public class CodeServiceImpl implements CodeService{

    private final EmployeeRepository employeeRepo;
    private final RoleRepository roleRepository;

    public CodeServiceImpl(EmployeeRepository employeeRepo, RoleRepository roleRepository) {
        this.employeeRepo = employeeRepo;
        this.roleRepository = roleRepository;
    }

    @Override
    public String generateEmployeeCode(Employee employee) {

        String prefix = employee.getWorkArea().toString().substring(3).toLowerCase();
        String lastDigits = employee.getDocumentNumber()
                .substring(employee.getDocumentNumber().length() - 4);
        Long count = employeeRepo.count() + 1;

        return String.format("%s%s-%02d", prefix, lastDigits, count);
    }

    @Override
    public String generateRoleCode(Role role){
        String prefix = role.getRoleName().substring(0, Math.min(3, role.getRoleName().length())).toUpperCase();
        Long count = roleRepository.count() + 1;

        return String.format("%s-%03d", prefix, count);
    }

}
