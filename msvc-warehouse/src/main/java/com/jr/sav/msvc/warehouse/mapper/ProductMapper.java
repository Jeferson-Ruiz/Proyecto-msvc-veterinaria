package com.jr.sav.msvc.warehouse.mapper;

import org.springframework.stereotype.Component;
import com.jr.sav.msvc.warehouse.dto.ProductDto;
import com.jr.sav.msvc.warehouse.entities.Product;

@Component
public class ProductMapper {

    public ProductDto toDto(Product entity){
        ProductDto dto = new ProductDto();
        dto.getId();
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

    public Product toEntity(ProductDto dto){
        Product entity = new Product();
        entity.getId();
        entity.setCommercialName(dto.getCommercialName());
        entity.setMark(dto.getMark());
        entity.setQuantityStock(dto.getQuantityStock());
        entity.setPresentation(dto.getPresentation());
        entity.setPurchasePrice(dto.getPurchasePrice());
        entity.setSalesPrice(dto.getSalesPrice());
        entity.setCategory(dto.getCategory());
        entity.setRegistrationDate(dto.getRegistrationDate());
        return entity;
    }

}
