package com.jeferson.msvc.users.respositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jeferson.msvc.users.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String name);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
