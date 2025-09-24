package com.jeferson.msvc_sav_workstaff.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.status =:status")
    List<Employee> findAllByStatus(@Param("status") EmployeeStatus status);

    Optional<Employee> findByDocumentNumber(String documentNumber);

    @Query("SELECT e FROM Employee e WHERE TYPE(e) = :clazz AND e.status =:status")
    List<Employee> findByType(Class<? extends Employee> clazz, @Param("status") EmployeeStatus status);

    @Modifying
    @Query("update Employee set email=:email where employeeId=:employeeId")
    void updateEmail(@Param("employeeId") Long employeeId, @Param("email") String email);

    @Modifying
    @Query("update Employee set phoneNumber=:phoneNumber where employeeId=:employeeId")
    void updatePhoneNumber(@Param("employeeId") Long employeeId, @Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("update Employee set contractType=:contractType where employeeId=:employeeId")
    void updateContractType(@Param("employeeId") Long employeeId, @Param("contractType") ContractType contractType);

}
