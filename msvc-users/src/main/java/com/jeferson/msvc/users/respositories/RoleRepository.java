package com.jeferson.msvc.users.respositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jeferson.msvc.users.entities.Role;
import com.jeferson.msvc.users.entities.Roles;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Roles name);
}
