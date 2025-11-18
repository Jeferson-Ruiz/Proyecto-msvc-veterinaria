package com.jr.sav.msvc.warehouse.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    
    @NotBlank(message = "El nombre del producto no puede ser nulo")
    @Size(max = 255, message = "El nombre del producto es muy largo")
    private String productName;

    @NotBlank(message = "El grupo del producto no puede ser nulo")
    private String subgroup;

    @NotBlank
    private String productBrand;

    @NotBlank(message = "La presentacion no puede ser nulo")
    private String presentation;

    @NotNull
    @DecimalMin(value = "0.0", message = "El preecio no puede ser negativo")
    private Double purchasePrice;

    @NotNull
    @DecimalMin(value = "0.0", message = "El preecio de venta debe ser mayor a 0")
    private Double salesPrice;

    @NotNull
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer quantityStock;

    @NotBlank(message = "El código de categoría es obligatorio\"")
    private String categoryCode;
}
