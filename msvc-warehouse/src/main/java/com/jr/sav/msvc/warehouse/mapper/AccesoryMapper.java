package com.jr.sav.msvc.warehouse.mapper;

import org.springframework.stereotype.Component;
import com.jr.sav.msvc.warehouse.dto.AccesoryDto;
import com.jr.sav.msvc.warehouse.entities.Accessory;


@Component
public class AccesoryMapper {

    public static AccesoryDto toDto(Accessory entity){
        AccesoryDto dto = new AccesoryDto();
        dto.setCommercialName(entity.getCommercialName());
        dto.setMark(entity.getMark());
        dto.setQuantityStock(entity.getQuantityStock());
        dto.setPresentation(entity.getPresentation());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setSalesPrice(entity.getSalesPrice());
        dto.setCategory(entity.getCategory());
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setMaterial(entity.getMaterial());
        dto.setSize(entity.getSize());
        return dto;
    }
}