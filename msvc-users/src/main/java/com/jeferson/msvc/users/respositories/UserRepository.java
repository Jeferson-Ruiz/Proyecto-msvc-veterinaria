package com.jeferson.msvc.users.respositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jeferson.msvc.users.entities.User;
import com.jeferson.msvc.users.entities.UserStatus;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String name);

    Optional<User> findByUserCode(String userCode);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findAllByStatus(UserStatus status);
}
