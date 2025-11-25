package com.msvc.invoice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDto {
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private Double subtotal;
}
