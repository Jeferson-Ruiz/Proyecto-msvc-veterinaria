package com.jr.msvc.medicalcontrol.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "medical_treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treatment")
    private Long idTreatment;

    @Column(name = "treatment_code", nullable = false, unique = true)
    private String treatmentCode;

    @Column(name = "tre_name", nullable = false, length = 100)
    private String nameTreatment;

    @Column(name = "pet_name", nullable =  false, length =  50)
    private String petName;

    @Column(name = "tre_description", nullable = false, length = 100)
    private String description;

    @Column(name = "tre_registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "tre_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "tre_end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "tre_instructions", nullable = false)
    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consultation", nullable = false)
    private Consultation consultation;

}
