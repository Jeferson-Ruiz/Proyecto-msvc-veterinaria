package com.jr.sav.msvc.warehouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Food extends Product {
    
    @Column(name = "foo_animal_type", length = 30)
    private String animalType;

    @Column(name = "foo_weight")
    private double weight;

    @Column(name = "foo_expiration_date", nullable = false)
    private LocalDate expirationDate;

}
