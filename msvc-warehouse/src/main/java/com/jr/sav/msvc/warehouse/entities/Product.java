package com.jr.sav.msvc.warehouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pro_commercial_name", nullable = false, length = 30)
    private String commercialName;

    @Column(name = "pro_mark", nullable = false, length = 50)
    private String mark;
    
    @Column(name = "pro_quantity_stock", nullable = false)
    private Integer quantityStock;

    @Column(name = "pro_presentation")
    private String presentation;
    
    @Column(name = "pro_purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "pro_purchase_price", nullable = false)
    private Double salesPrice;

    @Column(name = "pro_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "med_purchase_price", nullable = false)
    private LocalDate registrationDate;
}