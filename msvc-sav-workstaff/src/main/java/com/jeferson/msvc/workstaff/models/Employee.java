package com.jeferson.msvc.workstaff.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long employeeId;

    @Column(name = "emp_code", nullable = false, unique = true)
    private String employeeCode;

    //Información de Empleado
    @Column(name = "emp_name", length = 20, nullable = false)
    private String name;

    @Column(name = "emp_last_name", length = 20, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_docu_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "emp_docu_number", nullable = false, unique = true)
    private String documentNumber;

    @Column(name = "emp_email", nullable = false, length = 50)
    private String email;

    @Column(name = "emp_phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "emp_date_birth", nullable = false)
    private LocalDate dateOfBirth;

    //Estado laboral
    @Column(name = "emp_wotk_area")
    @Enumerated(EnumType.STRING)
    private WorkArea workArea;

    @Column(name = "emp_contract_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column(name = "emp_work_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @Column(name = "emp_registration_date")
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "employee_role",
        joinColumns = @JoinColumn(name = "emp_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ActionInformation> actionInformations;
}