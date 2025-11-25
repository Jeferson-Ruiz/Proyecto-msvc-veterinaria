package com.msvc.invoice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {
    private String productCode;
    private String productName;
    private Integer quantity;
}
