package com.jeferson.msvc.workstaff.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jeferson.msvc.workstaff.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

    @Query("SELECT r FROM Role r WHERE r.roleCode =:roleCode")
    Optional<Role> findByCode(@Param("roleCode") String roleCode);

    @Query("SELECT r FROM Role r WHERE r.roleName =:roleName")
    Optional<Role> findByName(@Param("roleName") String roleName);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.roleName =:roleName")
    boolean existByName(@Param("roleName") String roleName);
}