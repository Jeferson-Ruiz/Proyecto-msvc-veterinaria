package com.jeferson.sav_msvc_staff.personnel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends Person{
    private Long licenseNumber;
    private String specialty;
    private String contractType;
}