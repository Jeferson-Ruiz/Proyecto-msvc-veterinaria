package com.msvc.invoice.dto;

import java.util.List;
import com.msvc.invoice.entities.DocumentType;
import com.msvc.invoice.entities.InvoiceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequestDto {
    private InvoiceType invoiceType;
    private String consultationCode;
    private String customerName;
    private DocumentType documentType;
    private String customerDocument;
    private List<ItemRequestDto> items;

}
