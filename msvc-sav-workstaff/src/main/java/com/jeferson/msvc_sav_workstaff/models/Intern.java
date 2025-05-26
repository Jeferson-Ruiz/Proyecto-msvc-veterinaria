package com.jeferson.msvc_sav_workstaff.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("INTERN")
public class Intern extends Employee {

    @Column(name = "int_education_institute", nullable = true, length = 30)
    private String educationInstitute;

    @Column(name = "int_level_academic", nullable = true, length = 20)
    private String levelAcademic;

}