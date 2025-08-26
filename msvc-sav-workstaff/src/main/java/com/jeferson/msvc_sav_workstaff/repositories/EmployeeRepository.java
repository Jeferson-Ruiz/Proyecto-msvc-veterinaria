package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.WorkArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByDocumentNumber(String documentNumber);

    List<Employee> findByWorkArea(WorkArea workArea);

    @Modifying
    @Query("update Employee set email=:email where employeeId=:employeeId")
    void updateEmail(@Param("employeeId") Long employeeId, @Param("email") String email);

    @Modifying
    @Query("update Employee set phoneNumber=:phoneNumber where employeeId=:employeeId")
    void updatePhoneNumber(@Param("employeeId") Long employeeId, @Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("update Employee set contractType=:contractType where employeeId=:employeeId")
    void updateContractType(@Param("employeeId") Long employeeId, @Param("contractType") ContractType contractType);

    @Modifying
    @Query("update Employee set workArea=:workArea where employeeId =:employeeId")
    void updateWorkArea(@Param ("employeeId") Long employeeId, @Param("workArea") WorkArea workArea);

}
