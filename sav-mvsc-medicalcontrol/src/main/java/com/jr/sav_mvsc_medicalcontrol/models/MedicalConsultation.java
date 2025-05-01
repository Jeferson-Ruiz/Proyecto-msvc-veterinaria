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
public class MedicalConsultation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultatio")
    private Long idConsultation;

    @Column(name = "mcon_id_pet")
    private Long idPet;

    @Column(name = "mcon_date", nullable = false)
    private LocalDate date;

    @Column(name = "mcon_reason", length = 20, nullable = false)
    private String reason;

    @Column(name = "mcon_observatio", length = 30, nullable = false)
    private String observations;

    @Column(name = "mcon_veterinary_id", nullable = false)
    private Long veterinaryId;
}