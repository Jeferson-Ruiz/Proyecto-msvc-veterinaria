package com.jeferson.msvc_sav_workstaff.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("VET")
public class Vet extends Employee {

    @Column(name = "work_area", nullable = false)
    @Enumerated(EnumType.STRING)
    private VetRoles vetRoles;

    @Column(name = "vet_speciality", nullable = true,  length = 20)
    private String speciality;

    @Column(name = "vet_prof_card", nullable = false)
    private String professionalCard;
}