package com.jr.sav.msvc.warehouse.dto;

import com.jr.sav.msvc.warehouse.entities.Comparison;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequestDto {

    @NotNull
    private int quantityStock;

    @NotNull(message = "La comparación es obligatoria")
    private Comparison comparison;

}
