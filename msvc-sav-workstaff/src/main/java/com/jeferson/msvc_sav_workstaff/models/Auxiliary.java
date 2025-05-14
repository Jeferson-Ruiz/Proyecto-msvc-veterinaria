package com.jeferson.msvc_sav_workstaff.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "auxiliaries")
@DiscriminatorValue("AUXILIARY")
public class Auxiliary extends Employee{
    @Column(name = "aux_work_area", nullable = false, length = 20)
    private String workArea;
    @Column(name = "aux_academic_certificate", nullable = false, length = 20)
    private String academicCertificate;
}