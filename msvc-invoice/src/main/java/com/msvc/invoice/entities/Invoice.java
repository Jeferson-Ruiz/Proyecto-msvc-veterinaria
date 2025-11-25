package com.msvc.invoice.entities;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_id", unique = true)
    private Long id;

    @Column(name = "inv_code", nullable = false, unique =  true)
    private String invoiceCode;

    @Column(name = "inv_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @Column(name = "inv_consultation_code")
    private String consultationCode; //Opcional

    @Column(name = "inv_customer_name", nullable = false)
    private String customerName;

    @Column(name = "inv_document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "inv_customer_document", nullable = false)
    private String customerDocument;

    @ElementCollection    
    @CollectionTable(name = "invoice_items", joinColumns = @JoinColumn(name = "invoice_id"))    
    private List<InvoiceItem> items;

    @Column(name = "inv_invoice_total", nullable = false)
    private Double total;

    @Column(name = "inv_date", nullable = false)
    private LocalDate date;

}
