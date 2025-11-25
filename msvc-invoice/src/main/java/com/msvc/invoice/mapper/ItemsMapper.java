package com.msvc.invoice.mapper;

import org.springframework.stereotype.Component;
import com.msvc.invoice.dto.ItemRequestDto;
import com.msvc.invoice.dto.ItemResponseDto;
import com.msvc.invoice.entities.InvoiceItem;

@Component
public class ItemsMapper {

    public InvoiceItem toEntity(ItemRequestDto dto){
        InvoiceItem entity = new InvoiceItem();
        entity.setProductName(dto.getProductName());
        entity.setProductCode(dto.getProductCode());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    public ItemResponseDto toDto(InvoiceItem entity){
        ItemResponseDto dto = new ItemResponseDto();
        dto.setProductName(entity.getProductName());
        dto.setUnitPrice(entity.getSalesPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }
}
