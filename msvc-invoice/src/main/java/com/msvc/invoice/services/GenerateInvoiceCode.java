package com.msvc.invoice.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import com.msvc.invoice.repositories.InvoiceRepository;

@Component
public class GenerateInvoiceCode {

    private final InvoiceRepository invoiceRepository;

    public GenerateInvoiceCode(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    
    public String generateCode(LocalDate invoiceDate) {
        String dateStr = invoiceDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long consecutivo = invoiceRepository.countBydate(invoiceDate) + 1;
        
        String code = String.format("FAC%s%04d", dateStr, consecutivo);
        
        // Verificar por si acaso (aunque consecutivo debería ser único)
        while (invoiceRepository.existsByinvoiceCode(code)) {
            consecutivo++;
            code = String.format("FAC%s%04d", dateStr, consecutivo);
        }
        
        return code;
    }
}
