package com.jeferson.msvc.workstaff.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.WorkArea;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.status = 'ACTIVE'")
    List<Employee> findAllActiveEmployee();

    Optional<Employee> findByDocumentNumber(String documentNumber);

    @Query("SELECT e FROM Employee e WHERE e.employeeCode=:employeeCode")
    Optional<Employee> findByEmployeeCode(@Param("employeeCode") String employeeCode);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.email =:email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.phoneNumber =:phoneNumber")
    boolean existsByPhone(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.documentNumber =:documentNumber")
    boolean existsByDocument(@Param("documentNumber") String documentNumber);

    /********************************
     * Metodos para el filtro
     ********************************/

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.roles r WHERE e.workArea = :workArea AND r.roleName = :roleName AND e.status = :status")
    List<Employee> findByAreaRoleAndStatus(
            @Param("workArea") WorkArea workArea,
            @Param("roleName") String roleName,
            @Param("status") EmployeeStatus status);

    List<Employee> findByWorkArea(WorkArea workArea);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.roles r WHERE r.roleName = :roleName")
    List<Employee> findByRoleName(@Param("roleName") String roleName);

    List<Employee> findByStatus(EmployeeStatus status);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.roles r WHERE e.workArea = :workArea AND r.roleName = :roleName")
    List<Employee> findByAreaAndRole(
            @Param("workArea") WorkArea workArea,
            @Param("roleName") String roleName);

    List<Employee> findByWorkAreaAndStatus(WorkArea workArea, EmployeeStatus status);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.roles r WHERE r.roleName = :roleName AND e.status = :status")
    List<Employee> findByRoleAndStatus(
            @Param("roleName") String roleName,
            @Param("status") EmployeeStatus status);
}
