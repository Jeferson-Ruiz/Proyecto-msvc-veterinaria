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
@DiscriminatorValue("AUXILIARY")
public class Auxiliary extends Employee {

    @Column(name = "work_area", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuxiliaryRoles auxiliaryRoles;

    @Column(name = "aux_academic_certificate", nullable = true, length = 50)
    private String academicCertificate;

    @Override
    public WorkArea getArea() {
        return WorkArea.AUXILIARY;
    }

}