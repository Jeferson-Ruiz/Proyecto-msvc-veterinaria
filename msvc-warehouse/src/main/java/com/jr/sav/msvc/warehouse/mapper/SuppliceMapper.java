package com.jr.sav.msvc.warehouse.mapper;

import org.springframework.stereotype.Component;
import com.jr.sav.msvc.warehouse.dto.SuppliceDto;
import com.jr.sav.msvc.warehouse.entities.Supplie;

@Component
public class SuppliceMapper {
    public SuppliceDto toDto(Supplie entity){
        SuppliceDto dto = new SuppliceDto();
        dto.setCommercialName(entity.getCommercialName());
        dto.setMark(entity.getMark());
        dto.setQuantityStock(entity.getQuantityStock());
        dto.setPresentation(entity.getPresentation());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setSalesPrice(entity.getSalesPrice());
        dto.setCategory(entity.getCategory());
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setUnitMeasure(entity.getUnitMeasure());
        dto.setDisposable(entity.isDisposable());
        return dto;
    }
}
