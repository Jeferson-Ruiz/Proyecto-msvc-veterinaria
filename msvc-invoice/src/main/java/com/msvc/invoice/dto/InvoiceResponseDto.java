package com.msvc.invoice.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponseDto {

    private String invoiceCode;
    private LocalDate date;
    private String customerName;
    private String customerDocument;
    private List<ItemResponseDto> items;
    
    private Double total;
}
