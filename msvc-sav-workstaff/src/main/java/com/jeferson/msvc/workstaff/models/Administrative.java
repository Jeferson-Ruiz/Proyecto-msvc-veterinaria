package com.jeferson.msvc.workstaff.models;

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
@DiscriminatorValue("ADMINISTRATIVE")
public class Administrative extends Employee {

    @Column(name = "work_area", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdministrativeRoles administrativeRoles;

    @Column(name = "adm_academic_title", length = 40, nullable = false)
    private String academicTitle;

    @Column(name = "adm_prof_card", unique = true, length = 20)
    private String professionalCard;

    @Override
    public WorkArea getArea() {
        return WorkArea.ADMINISTRATIVE;
    }

}