package com.jeferson.msvc.workstaff.services;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jeferson.msvc.workstaff.models.Role;
import com.jeferson.msvc.workstaff.models.WorkArea;
import com.jeferson.msvc.workstaff.repositories.RoleRepository;

@Component
public class RoleValidation {

    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> roleValidation(WorkArea area, Set<String> rolesName) {

            // buscar roles
            Set<Role> roles = roleRepository.findByName(rolesName);
    
            // Verificar que existen los roles existan
            if (roles.size() != rolesName.size()) {
                throw new IllegalArgumentException("Algunos roles no existen en el sistema");
            }

            for (Role rol : roles) {
                if (!area.equals(rol.getArea())) {
                    throw new IllegalArgumentException(String.format("Rol "+ rol.getRoleName() +" no es válido para un empleado del área "+ area,
                    rol.getRoleName(),
                    rol.getArea(),
                    area));
                }
            }
        return roles;
            
  
    }
}
