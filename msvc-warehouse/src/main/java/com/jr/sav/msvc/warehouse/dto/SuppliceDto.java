package com.jr.sav.msvc.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuppliceDto extends ProductDto{
    private String unitMeasure;
    private boolean disposable;
}
