package com.jeferson.msvc.workstaff.services;

import java.util.Set;
import org.springframework.stereotype.Component;
import com.jeferson.msvc.workstaff.models.Role;
import com.jeferson.msvc.workstaff.models.WorkArea;

@Component
public class RoleValidation {
    public void roleValidation(WorkArea area, Set<Role> roles) {
        for (Role rol : roles) {
            if (!area.equals(rol.getArea())) {
                throw new IllegalArgumentException(String.format(
                "Rol '%s' (Área: %s) no es válido para un empleado del área %s",
                rol.getRoleName(),
                rol.getArea(),
                area));
            }
        }
    }
}
