package com.jeferson.msvc_sav_workstaff.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "inters")
@DiscriminatorValue("Inter")
public class Intern extends Employee {
    
    @Column(name = "int_education_institute", nullable = false, length = 30)
    private String educationInstitute;

    @Column(name = "int_level_academic", nullable = false, length = 20)
    private String levelAcademic;
    
}