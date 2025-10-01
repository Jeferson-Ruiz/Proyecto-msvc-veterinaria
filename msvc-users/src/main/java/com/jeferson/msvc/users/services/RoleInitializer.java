package com.jeferson.msvc.users.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.jeferson.msvc.users.entities.Role;
import com.jeferson.msvc.users.entities.Roles;
import com.jeferson.msvc.users.respositories.RoleRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (Roles roleEnum : Roles.values()) {
            roleRepository.findByName(roleEnum)
                .orElseGet(() -> roleRepository.save(new Role(null, roleEnum)));
        }
    }

}
