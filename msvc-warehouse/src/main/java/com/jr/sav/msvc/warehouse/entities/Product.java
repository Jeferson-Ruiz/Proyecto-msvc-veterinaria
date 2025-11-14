package com.jr.sav.msvc.warehouse.entities;

import java.time.LocalDateTime;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pro_code", nullable = false, unique = true, length = 20)
    private String productCode;

    @Column(name = "pro_commercial_name", nullable = false, length = 30)
    private String productName;
    
    @Column(name = "pro_subgroup", nullable = false, length = 30)
    private String subgroup;  

    @Column(name = "pro_mark", nullable = false)
    private String productBrand;

    @Column(name = "pro_presentation")
    private String presentation;
    
    @Column(name = "pro_purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "pro_sales_price", nullable = false)
    private Double salesPrice;

    @Column(name = "pro_quantity_stock", nullable = false)
    private Integer quantityStock;

    @Column(name = "pro_activo", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "med_purchase_price", nullable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private Category category;
}