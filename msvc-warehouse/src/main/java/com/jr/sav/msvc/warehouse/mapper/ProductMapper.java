package com.jr.sav.msvc.warehouse.mapper;

import org.springframework.stereotype.Component;
import com.jr.sav.msvc.warehouse.dto.ProductDto;
import com.jr.sav.msvc.warehouse.entities.Product;

@Component
public class ProductMapper {

    public ProductDto toDto(Product entity){
        ProductDto dto = new ProductDto();
        dto.setCommercialName(entity.getCommercialName());
        dto.setMark(entity.getMark());
        dto.setQuantityStock(entity.getQuantityStock());
        dto.setPresentation(entity.getPresentation());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setSalesPrice(entity.getSalesPrice());
        dto.setCategory(entity.getCategory());
        dto.setRegistrationDate(entity.getRegistrationDate());
        return dto;
    }

}
