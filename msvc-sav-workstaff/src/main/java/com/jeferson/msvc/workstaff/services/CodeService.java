package com.jeferson.msvc.workstaff.services;

import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.models.Role;

public interface CodeService {

    String generateEmployeeCode(Employee employee);
    String generateRoleCode(Role role);
}
