package com.jr.sav_msvc_pet.models;

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
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owne_id", unique = true)
    private Long idOwner;

    @Column(name = "owne_name", nullable = false)
    private String name;

    @Column(name = "owne_last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "owne_documt_type", nullable = false, length = 15)
    private String documentType;

    @Column(name = "owne_documt_number", nullable = false)
    private Long documentNumber;

    @Column(name = "owne_email", nullable = false, length = 50)
    private String email;

    @Column(name = "owne_phone_number", nullable = false)
    private Long phoneNumber;

    @Column(name = "owne_data_of_recording", nullable = false)
    private LocalDate dateOfRecording;
}