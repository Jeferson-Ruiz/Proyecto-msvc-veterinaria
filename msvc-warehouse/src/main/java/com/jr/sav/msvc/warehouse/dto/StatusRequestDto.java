package com.jr.sav.msvc.warehouse.dto;

import com.jr.sav.msvc.warehouse.entities.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusRequestDto {

    @NotNull(message = "Ingresar un estado valido")
    private ProductStatus productStatus;

}
