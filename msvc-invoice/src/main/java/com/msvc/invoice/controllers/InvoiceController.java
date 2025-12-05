package com.msvc.invoice.controllers;

import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.msvc.invoice.dto.DateRequestDto;
import com.msvc.invoice.dto.InvoiceRequestDto;
import com.msvc.invoice.dto.InvoiceResponseDto;
import com.msvc.invoice.services.InvoiceService;
import jakarta.validation.Valid;

@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> saveInvoice(@RequestBody InvoiceRequestDto invoice){
        InvoiceResponseDto invoiceDto = invoiceService.save(invoice);
        return ResponseEntity.ok(invoiceDto);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable String code){
        InvoiceResponseDto invoiceDto = invoiceService.findByCode(code);
        return ResponseEntity.ok(invoiceDto);
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<?> getAllByCustomerDocument(@PathVariable String document){
        List<InvoiceResponseDto> invoiceDto = invoiceService.findByCustomerDocument(document);
        return ResponseEntity.ok(invoiceDto);
    }

    @GetMapping("/consultation/{code}")
    public ResponseEntity<?> getByConsultationCode(@PathVariable String code){
        InvoiceResponseDto invoice = invoiceService.findByConsultationCode(code);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/date")
    public ResponseEntity<?> getAllByDate(@RequestBody @Valid DateRequestDto request){
        List<InvoiceResponseDto> invoices = invoiceService.findAllByDate(request.getComparison(), request.getDate());
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/code/{code}/pdf")
    public ResponseEntity<?> downloadInvoicePdf(@PathVariable String code){
        byte[] pdfBytes = invoiceService.generateInvoicePdf(code);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("factura-" + code + ".pdf")
                .build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
