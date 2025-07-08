package com.jr.sav.msvc.warehouse.mapper;

import com.jr.sav.msvc.warehouse.dto.FoodDto;
import com.jr.sav.msvc.warehouse.entities.Food;

public class FoodMapper {
    public static FoodDto toDto(Food entity){
        FoodDto dto = new FoodDto();
        dto.setCommercialName(entity.getCommercialName());
        dto.setMark(entity.getMark());
        dto.setQuantityStock(entity.getQuantityStock());
        dto.setPresentation(entity.getPresentation());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setSalesPrice(entity.getSalesPrice());
        dto.setCategory(entity.getCategory());
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setAnimalType(entity.getAnimalType());
        dto.setWeight(entity.getWeight());
        dto.setExpirationDate(entity.getExpirationDate());

        return dto;
    }
}
