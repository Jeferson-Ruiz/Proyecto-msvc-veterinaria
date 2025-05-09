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
@Data
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long idPet;

    @Column(name = "pet_name", length = 20, nullable = false)
    private String name;

    @Column(name = "pet_specie", length = 20, nullable = false)
    private String specie;

    @Column(name = "pet_breed", length = 20, nullable = false)
    private String breed;

    @Column(name = "pet_sex", length = 1, nullable = false)
    private String sex;

    @Column(name = "pet_date_of_brith", nullable = false)
    private LocalDate dateOfBrith;

    @Column(name = "pet_date_of_recording", nullable = false)
    private LocalDate dateOfRecording;

    @Column(name = "pet_ower_id", nullable = false)
    private Long owerId;
}
