package com.jr.sav_mvsc_medicalcontrol.models;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "medical_consultations", uniqueConstraints = {@UniqueConstraint(columnNames = {"con_vet_id", "con_citation_date"})})
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultation", unique = true)
    private Long idConsultation;

    @Column(name = "con_name", nullable = false)
    private String petName;

    @Column(name = "con_reason", nullable = false, length = 50)
    private String reason;

    @Column(name = "con_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "con_citation_date", nullable = false)
    private LocalDateTime citationDate;

    @Column(name = "con_observation", length = 255)
    private String observations;

    @Column(name = "con_vet_id", nullable = false)
    private Long vetId;

    @Column(name = "con_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private List<Treatment> treatments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet", nullable = false)
    private Pet pet;

}