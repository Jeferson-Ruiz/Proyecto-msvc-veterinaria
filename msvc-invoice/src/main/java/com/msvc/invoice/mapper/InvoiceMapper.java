package com.msvc.invoice.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.msvc.invoice.dto.InvoiceRequestDto;
import com.msvc.invoice.dto.InvoiceResponseDto;
import com.msvc.invoice.dto.ItemResponseDto;
import com.msvc.invoice.entities.Invoice;

@Component
public class InvoiceMapper {

    @Autowired
    private ItemsMapper itemsMapper;

    public InvoiceResponseDto toDto(Invoice invoice){
        InvoiceResponseDto dto = new InvoiceResponseDto();
        dto.setInvoiceCode(invoice.getInvoiceCode());
        dto.setDate(invoice.getDate());
        dto.setCustomerName(invoice.getCustomerName());
        dto.setCustomerDocument(invoice.getCustomerDocument());
        dto.setTotal(invoice.getTotal());

          if(invoice.getItems() != null) {
            List<ItemResponseDto> itemDtos = invoice.getItems().stream()
                .map(itemsMapper::toDto)
                .collect(Collectors.toList());
            dto.setItems(itemDtos);
        }

        return dto;
    }

    public Invoice toEntity(InvoiceRequestDto dto){
        Invoice entity = new Invoice();
        entity.setInvoiceType(dto.getInvoiceType());
        entity.setConsultationCode(dto.getConsultationCode());
        entity.setCustomerName(dto.getCustomerName());
        entity.setDocumentType(dto.getDocumentType());
        entity.setCustomerDocument(dto.getCustomerDocument());
        return entity;
    }

}
