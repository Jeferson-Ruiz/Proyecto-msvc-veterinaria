package com.jr.sav.msvc.warehouse.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jr.sav.msvc.warehouse.entities.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private String productCode;
    private String productName;
    private String subgroup;
    private String presentation;
    private Double purchasePrice;
    private Double salesPrice;
    private Integer quantityStock;
    private String categoryName;
    private ProductStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime registrationDate;

}
