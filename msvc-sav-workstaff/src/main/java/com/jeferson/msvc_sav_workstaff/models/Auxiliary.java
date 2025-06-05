package com.jeferson.msvc_sav_workstaff.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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

    @Column(name = "aux_academic_certificate", nullable = true, length = 20)
    private String academicCertificate;

    @Column(name = "aux_work_area", nullable = true, length = 20)
    private String workArea;
}