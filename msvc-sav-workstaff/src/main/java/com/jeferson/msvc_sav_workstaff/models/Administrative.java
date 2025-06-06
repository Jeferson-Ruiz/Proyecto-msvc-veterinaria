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
@DiscriminatorValue("ADMINISTRATIVE")
public class Administrative extends Employee {

    @Column(name = "adm_academic_title", length = 30, nullable = true)
    private String academicTitle;

    @Column(name = "adm_work_area", nullable = true, length = 20)
    private String workArea;
}