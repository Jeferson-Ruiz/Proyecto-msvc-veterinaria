package com.jr.sav.msvc.warehouse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String productName;
    private String subgroup;
    private String productBrand;
    private String presentation;
    private Double purchasePrice;
    private Double salesPrice;
    private Integer quantityStock;
    private String categoryCode;
}
