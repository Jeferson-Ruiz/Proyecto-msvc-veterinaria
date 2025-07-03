package com.jr.sav.msvc.warehouse.entities;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicamentos")
public class Medicines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "med_commercial_name", nullable = false, length = 30)
    private String commercialName;

    @Column(name = "med_activate_principle", nullable = false, length = 50)
    private String ActivePrinciple;

    @Column(name = "med_concentration_ml", nullable = false)
    private Integer concentration;

    @Column(name = "med_pharmaceutical_form", nullable = false, length = 30)
    private String pharmaceuticalForma;

    @Column(name = "med_route_administration", nullable = false, length = 30)
    private String routeAdministration;

    @Column(name = "med_laboratory", nullable = false, length = 30)
    private String laboratory;

    @Column(name = "med_lote", nullable = false, length = 30)
    private String lote;

    @Column(name = "med_expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "med_quantity_stock", nullable = false)
    private Integer quantityStock;
    
    @Column(name = "med_purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "med_purchase_price", nullable = false)
    private Double salesPrice;

    @Column(name = "med_purchase_price", nullable = false)
    private LocalDate registrationDate;
}