package com.msvc.invoice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductClientDto {

    private String productCode;
    private String productName;
    private String presentation;
    private Double salesPrice;
    private Integer quantityStock;
       
}
