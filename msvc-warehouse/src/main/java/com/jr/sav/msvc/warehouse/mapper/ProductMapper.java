package com.jr.sav.msvc.warehouse.mapper;

import org.springframework.stereotype.Component;
import com.jr.sav.msvc.warehouse.dto.ProductRequestDto;
import com.jr.sav.msvc.warehouse.dto.ProductResponseDto;
import com.jr.sav.msvc.warehouse.entities.Product;

@Component
public class ProductMapper {

    public ProductResponseDto toDto(Product entity){
        ProductResponseDto dto = new ProductResponseDto();
        dto.setProductCode(entity.getProductCode());
        dto.setProductName(entity.getProductName());
        dto.setSubgroup(entity.getSubgroup());
        dto.setPresentation(entity.getPresentation());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setSalesPrice(entity.getSalesPrice());
        dto.setQuantityStock(entity.getQuantityStock());
        dto.setCategoryName(entity.getCategory().getCategoryName());
        dto.setStatus(entity.getStatus());
        dto.setRegistrationDate(entity.getRegistrationDate());

        return dto;
    }

    public Product toEntity(ProductRequestDto dto){
        Product entity = new Product();
        entity.setProductName(dto.getProductName());
        entity.setSubgroup(dto.getSubgroup());
        entity.setProductBrand(dto.getProductBrand());
        entity.setPresentation(dto.getPresentation());
        entity.setPurchasePrice(dto.getPurchasePrice());
        entity.setSalesPrice(dto.getSalesPrice());
        entity.setQuantityStock(dto.getQuantityStock());
        return entity;
    }

}
