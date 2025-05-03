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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "medical_consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultation")
    private Long idConsultation;

    @Column(name = "con_id_pet")
    private Long idPet;

    @Column(name = "con_date", nullable = false)
    private LocalDate date;

    @Column(name = "con_reason", length = 20, nullable = false)
    private String reason;

    @Column(name = "con_observation", length = 30, nullable = false)
    private String observations;

    @Column(name = "con_veterinary_id", nullable = false)
    private Long veterinaryId;
}