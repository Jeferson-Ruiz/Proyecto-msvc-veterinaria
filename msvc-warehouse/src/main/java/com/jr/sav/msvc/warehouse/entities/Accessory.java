package com.jr.sav.msvc.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "accesories")
public class Accessory extends Product {
    
    @Column(name = "acc_material", length = 20)
    private String material;

    @Column(name = "acc_size", length = 20)
    private String size;
}