package com.msvc.invoice.services;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.msvc.invoice.entities.Invoice;
import com.itextpdf.kernel.geom.PageSize;
import java.io.ByteArrayOutputStream;

@Component
public class PdfGeneratorService {

    private final TemplateEngine templateEngine;

    public PdfGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generateInvoicePdf(Invoice invoice) {
        try {
            // 1. Preparar datos para el template
            Context context = new Context();
            context.setVariable("invoice", invoice);
            
            // 2. Generar HTML desde template
            String html = templateEngine.process("invoice", context);
            
            // 3. Convertir HTML a PDF
            return convertHtmlToPdf(html);
            
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF desde template", e);
        }
    }

    private byte[] convertHtmlToPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.LETTER);
            
            ConverterProperties properties = new ConverterProperties();
            HtmlConverter.convertToPdf(html, pdfDocument, properties);
            
            return outputStream.toByteArray();
            
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo HTML a PDF", e);
        }
    }
}   
