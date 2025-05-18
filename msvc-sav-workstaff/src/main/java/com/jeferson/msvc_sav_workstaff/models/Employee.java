package com.jeferson.msvc_sav_workstaff.models;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "emp_job_position", discriminatorType = DiscriminatorType.STRING)
@Table(name = "employees")
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long idEmployee;

    @Column(name = "emp_name", length = 20, nullable = false)
    private String name;

    @Column(name = "emp_last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "emp_docu_type", nullable = false, length = 20)
    private String documentType;

    @Column(name = "emp_docu_number", nullable = false, unique = true)
    private Long documentNumber;

    @Column(name = "emp_date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "emp_email", nullable = false, length = 50)
    private String email;

    @Column(name = "emp_phone_number", nullable = false)
    private Long phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_contract_type", nullable = false, length = 20)
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_job_position", insertable = false, updatable = false)
    private JobPosition jobPosition;

    @Column(name = "emp_work_status", nullable = false)
    private Boolean workStatus;
}