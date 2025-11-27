package com.msvc.invoice.services;

import java.time.LocalDate;
import java.util.List;
import com.msvc.invoice.dto.InvoiceRequestDto;
import com.msvc.invoice.dto.InvoiceResponseDto;
import com.msvc.invoice.entities.Comparison;

public interface InvoiceService {

    InvoiceResponseDto save(InvoiceRequestDto invoiceDto);

    InvoiceResponseDto findByCode(String code);

    List<InvoiceResponseDto> findByCustomerDocument(String document);

    InvoiceResponseDto findByConsultationCode(String code);

    List<InvoiceResponseDto> findAllByDate(Comparison comparison, LocalDate date);

    byte[] generateInvoicePdf(String invoiceCode);
}
