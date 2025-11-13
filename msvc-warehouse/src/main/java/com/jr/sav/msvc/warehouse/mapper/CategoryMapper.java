package com.jr.sav.msvc.warehouse.mapper;

import org.springframework.stereotype.Component;

import com.jr.sav.msvc.warehouse.dto.CategoryRequestDto;
import com.jr.sav.msvc.warehouse.dto.CategoryResponseDto;
import com.jr.sav.msvc.warehouse.entities.Category;

@Component
public class CategoryMapper {

    public CategoryResponseDto toDto(Category category){
        CategoryResponseDto dto = new CategoryResponseDto();
        
        dto.setCategoryCode(category.getCategoryCode());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryPrefix(category.getCategoryPrefix()); // CORRECTO
        dto.setDescription(category.getDescription());
        return dto;
        
    }

    public Category toEntity(CategoryRequestDto categoryRequestDto){
        Category category = new Category();
        category.setCategoryName(categoryRequestDto.getCategoryName());
        category.setDescription(categoryRequestDto.getDescription());

        return category;
    }

}
