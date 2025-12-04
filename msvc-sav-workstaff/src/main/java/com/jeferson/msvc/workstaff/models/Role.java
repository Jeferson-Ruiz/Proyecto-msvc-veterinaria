package com.jeferson.msvc.workstaff.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long id;

    @Column(name = "rol_code", unique = true, nullable = false)
    private String roleCode;

    @Column(name = "rol_name", nullable = false)
    private String roleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_area" ,nullable = false)
    private WorkArea area;

    @Column(name = "rol_description")
    private String description;

    @Column(name = "rol_registration")
    private LocalDate registrationDate;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees = new HashSet<>() ;

}
