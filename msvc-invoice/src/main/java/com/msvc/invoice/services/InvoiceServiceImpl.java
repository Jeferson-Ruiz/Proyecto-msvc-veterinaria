package com.msvc.invoice.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.msvc.invoice.client.ConsultationClient;
import com.msvc.invoice.client.ProductClient;
import com.msvc.invoice.dto.ConsultationClientDto;
import com.msvc.invoice.dto.InvoiceRequestDto;
import com.msvc.invoice.dto.InvoiceResponseDto;
import com.msvc.invoice.dto.ItemRequestDto;
import com.msvc.invoice.dto.ProductClientDto;
import com.msvc.invoice.entities.Comparison;
import com.msvc.invoice.entities.Invoice;
import com.msvc.invoice.entities.InvoiceItem;
import com.msvc.invoice.entities.InvoiceType;
import com.msvc.invoice.mapper.InvoiceMapper;
import com.msvc.invoice.repositories.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private final InvoiceRepository invoiceRepository;
    private final ConsultationClient consultationClient;
    private final ProductClient productClient;
    private final InvoiceMapper invoiceMapper;
    private final GenerateInvoiceCode invoiceCode;
    private final PdfGeneratorService pdfService;


    public InvoiceServiceImpl(
        InvoiceRepository invoiceRepository,
        ConsultationClient consultationClient,
        ProductClient productClient,
        InvoiceMapper invoiceMapper,
        GenerateInvoiceCode invoiceCode,
        PdfGeneratorService pdfService)
        {
        this.invoiceRepository = invoiceRepository;
        this.consultationClient = consultationClient;
        this.productClient = productClient;
        this.invoiceMapper = invoiceMapper;
        this.invoiceCode = invoiceCode;
        this.pdfService = pdfService;
    }

    @Override
    public InvoiceResponseDto save(InvoiceRequestDto invoiceDto){
        if (invoiceDto.getInvoiceType().equals(InvoiceType.CONSULTATION)) {
           validateConsultation(invoiceDto.getConsultationCode());
        }

        //Mapeo a entidad y generar el codigo de la factura
        Invoice entity = invoiceMapper.toEntity(invoiceDto);
        entity.setDate(LocalDate.now());
        entity.setInvoiceCode(invoiceCode.generateCode(entity.getDate()));

        // Cache para productos
        Map<String, ProductClientDto> productsList = new HashMap<>();
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        //validar stock con factura
        for(ItemRequestDto item : invoiceDto.getItems()){
            ProductClientDto product = findByProductCode(item.getProductCode());

            productsList.put(item.getProductCode(), product);

            if (item.getQuantity() > product.getQuantityStock()) {
                throw new IllegalArgumentException("Inventario insuficiente para: " + product.getProductName());
            }
        }

        //creacion de los item de la factura:
        for(ItemRequestDto item : invoiceDto.getItems()){
            ProductClientDto product = productsList.get(item.getProductCode());

            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setProductCode(product.getProductCode());
            invoiceItem.setProductName(product.getProductName());
            invoiceItem.setSalesPrice(product.getSalesPrice());
            invoiceItem.setQuantity(item.getQuantity());

            //calcular subtotal de los productos
            invoiceItem.setSubtotal(product.getSalesPrice() * item.getQuantity());

            invoiceItems.add(invoiceItem);
        }

        //Calcular total
        Double total = calculateTotal(invoiceItems);

        //Setear items a la factura
        entity.setTotal(total);
        entity.setItems(invoiceItems);            

        //Actualizar el stock
        for(ItemRequestDto item: invoiceDto.getItems()){
            ProductClientDto product = productsList.get(item.getProductCode());
            int stockNew = product.getQuantityStock() - item.getQuantity();
            productClient.updateStockByCode(product.getProductCode(), stockNew);
        }
        return invoiceMapper.toDto(invoiceRepository.save(entity));
    }

    @Override
    public InvoiceResponseDto findByCode(String code){
        Invoice invoice = findInvoiceByCode(code);
        return invoiceMapper.toDto(invoice);            
    }

    @Override
    public List<InvoiceResponseDto> findByCustomerDocument(String document){
        List<Invoice> invoices = invoiceRepository.findByCustomerDocument(document);
        if (invoices.isEmpty()) throw new EntityNotFoundException("No se econtraron facturas para el cliente: "+ document);
        return invoices.stream().map(invoiceMapper::toDto).toList();
    }

    @Override
    public InvoiceResponseDto findByConsultationCode(String code){
        Invoice invoice = invoiceRepository.findByConsultationCode(code)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro factura asociada a la consulta: "+ code));
        return invoiceMapper.toDto(invoice);
    }

    @Override
    public List<InvoiceResponseDto> findAllByDate(Comparison comparison, LocalDate date){
        List<Invoice> invoices = switch (comparison){
            case LESS -> invoiceRepository.findByDate(date);
            case GREATER -> invoiceRepository.findByDateGreater(date);
            case EQUAL -> invoiceRepository.findByDate(date);
        };
        return invoices.stream().map(invoiceMapper::toDto).toList();
    }

    @Override
    public byte[] generateInvoicePdf(String invoiceCode){
        Invoice invoice = findInvoiceByCode(invoiceCode);
        return pdfService.generateInvoicePdf(invoice);
    }

    //helper
    private Invoice findInvoiceByCode(String code){
        Invoice invoice = invoiceRepository.findByCode(code)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro factura asociada al "+ code));
        return invoice;            
    }

    private void validateConsultation(String cosultationCode){
        ConsultationClientDto consultation = consultationClient.getConsultationByCode(cosultationCode);
        if (consultation == null) throw new EntityNotFoundException("No se encontro consulta asociada al codigo "+ cosultationCode);
    }

    private ProductClientDto findByProductCode(String productCode){
        ProductClientDto product = productClient.getByProductCode(productCode);
        if (product == null) throw new EntityNotFoundException("No se encontro producto asociado al codigo "+ productCode);
        return product;
    }

    private Double calculateTotal( List<InvoiceItem> invoiceItems){
        return invoiceItems
            .stream()
            .mapToDouble(InvoiceItem::getSubtotal)
            .sum();
    }

}
