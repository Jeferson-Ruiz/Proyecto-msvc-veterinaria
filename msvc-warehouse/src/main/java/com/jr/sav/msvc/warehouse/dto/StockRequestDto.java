package com.jr.sav.msvc.warehouse.dto;

import com.jr.sav.msvc.warehouse.entities.Comparison;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequestDto {
    private int stock;
    private Comparison comparison;

}
