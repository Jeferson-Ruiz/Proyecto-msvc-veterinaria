package com.jr.sav.msvc.warehouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medicines")
public class Medicine extends Product {
    
    @Column(name = "med_dose", nullable = false)
    private String dose;

    @Column(name = "med_expiration_date", nullable = false)
    private LocalDate expirationDate;
}
