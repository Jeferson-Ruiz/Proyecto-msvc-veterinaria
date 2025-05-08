package com.jr.sav_mvsc_medicalcontrol.models;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vaccines")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaccine", unique = true)
    private Long idVaccine;

    @Column(name = "vac_id_pet", nullable = false)
    private Long idPet;

    @Column(name = "vac_vaccine_name", nullable = false, length = 20)
    private String vaccineName;

    @Column(name = "vac_application_date", nullable = false)
    private LocalDate applicationData;

    @Column(name = "vac_nex_application_date")
    private LocalDate nextApplicationDate;

    @Column(name = "vac_lot", nullable = false, length = 20)
    private String lot;

    @Column(name = "vac_observation", length = 20)
    private String observations;

}