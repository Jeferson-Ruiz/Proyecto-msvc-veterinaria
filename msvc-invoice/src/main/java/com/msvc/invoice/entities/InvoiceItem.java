package com.msvc.invoice.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InvoiceItem {
    
    private String productCode;
    private String productName;
    private Integer quantity;
    private Double salesPrice;
    private Double subtotal;
}
