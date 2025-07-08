package com.jr.sav.msvc.warehouse.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto extends ProductDto {
    private String animalType;
    private double weight;
    private LocalDate expirationDate;
}
