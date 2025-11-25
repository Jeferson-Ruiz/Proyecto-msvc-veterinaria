package com.msvc.invoice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdStockRequestDto {

    @NotNull
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

}
