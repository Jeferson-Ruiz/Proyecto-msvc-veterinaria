package com.jr.sav.msvc.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "supplies")
public class Supplie extends Product {

    @Column(name = "sup_unit_measure",length = 10)
    private String unidadMedida;

    @Column(name = "sup_unit_disposable")
    private boolean desechable;
}