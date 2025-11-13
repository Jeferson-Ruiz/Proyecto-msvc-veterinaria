package com.jr.sav.msvc.warehouse.services;

import java.util.List;
import com.jr.sav.msvc.warehouse.dto.CategoryRequestDto;
import com.jr.sav.msvc.warehouse.dto.CategoryResponseDto;

public interface CategoryService {

    List<CategoryResponseDto> fingAllCategory();

    CategoryResponseDto saveCtegory(CategoryRequestDto category);

    CategoryResponseDto findByCode(String code);

    CategoryResponseDto findByName(String name);

    CategoryResponseDto findByPrefix(String prefix);
    
    void updateDescription(String code ,String description);

}
