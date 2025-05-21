package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.JobPosition;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRespository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByDocumentNumber(Long documentNumber);

    List<Employee> findByJobPosition(JobPosition jobPosition);

    @Modifying
    @Query("update Employee set email=:email where idEmployee=:idEmployee")
    void updateEmail(@Param("idEmployee") Long idEmployee, @Param("email") String email);

    @Modifying
    @Query("update Employee set phoneNumber=:phoneNumber where idEmployee=:idEmployee" )
    void updatePhoneNumber(@Param("idEmployee")Long idEmployee, @Param("phoneNumber") Long phoneNumber);

    @Modifying
    @Query("update Employee set contractType=:contractType where idEmployee=:idEmployee")
    void updateContractType (@Param("idEmployee") Long idEmployee, @Param("contractType") ContractType contractType);
}
