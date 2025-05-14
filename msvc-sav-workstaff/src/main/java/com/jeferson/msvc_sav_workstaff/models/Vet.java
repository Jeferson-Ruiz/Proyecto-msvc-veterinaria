package com.jeferson.msvc_sav_workstaff.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "veterinaries")
@DiscriminatorValue("Vet")
public class Vet extends Employee{

    @Column(name = "vet_speciality", length = 20)
    private String speciality;
    
    @Column(name = "vet_prof_card", nullable = false)
    private Long professionalCard;
}