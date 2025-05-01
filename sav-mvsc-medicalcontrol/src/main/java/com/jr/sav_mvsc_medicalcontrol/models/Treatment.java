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
@Entity
@Data
@Table(name = "medical_treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treatment")
    private Long idTreatment;

    @Column(name = "tre_id_consultation", nullable = false)
    private Long idConsultation;

    @Column(name = "tre_description", nullable = false, length = 30)
    private String description;
    
    @Column(name = "tre_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "tre_end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "tre_medicines", length = 100)
    private String medicines;

    @Column(name = "tre_instructions", length = 100)
    private String instructions;
    
}
