package com.jr.sav.msvc.warehouse.dto;

import java.time.LocalDate;
import com.jr.sav.msvc.warehouse.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String commercialName;
    private String mark;
    private Integer quantityStock;
    private String presentation;
    private Double purchasePrice;
    private Double salesPrice;
    private Category category;
    private LocalDate registrationDate;

}
