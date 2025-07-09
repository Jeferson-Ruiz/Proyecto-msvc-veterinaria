package com.jr.sav.msvc.warehouse.mapper;

import org.springframework.stereotype.Component;
import com.jr.sav.msvc.warehouse.dto.MedicineDto;
import com.jr.sav.msvc.warehouse.entities.Medicine;

@Component
public class MedicineMapper {

    public static MedicineDto toDto(Medicine entity){
        MedicineDto dto = new MedicineDto();
        dto.setCommercialName(entity.getCommercialName());
        dto.setMark(entity.getMark());
        dto.setQuantityStock(entity.getQuantityStock());
        dto.setPresentation(entity.getPresentation());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setSalesPrice(entity.getSalesPrice());
        dto.setCategory(entity.getCategory());
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setDose(entity.getDose());
        dto.setExpirationDate(entity.getExpirationDate());
        return dto;
    }

}
