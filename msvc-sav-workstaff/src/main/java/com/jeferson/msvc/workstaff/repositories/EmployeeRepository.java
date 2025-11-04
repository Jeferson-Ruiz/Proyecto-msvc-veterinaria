package com.jeferson.msvc.workstaff.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.status =:status")
    List<Employee> findAllByStatus(@Param("status") EmployeeStatus status);

    Optional<Employee> findByDocumentNumber(String documentNumber);

    @Query("SELECT e FROM Employee e WHERE TYPE(e) = :clazz AND e.status =:status")
    List<Employee> findByType(Class<? extends Employee> clazz, @Param("status") EmployeeStatus status);

    @Query("SELECT e FROM Employee e WHERE e.employeeCode=:employeeCode")
    Optional<Employee> findByEmployeeCode(@Param("employeeCode") String employeeCode);

}
