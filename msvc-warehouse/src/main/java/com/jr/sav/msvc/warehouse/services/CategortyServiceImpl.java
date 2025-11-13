package com.jr.sav.msvc.warehouse.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jr.sav.msvc.warehouse.dto.CategoryRequestDto;
import com.jr.sav.msvc.warehouse.dto.CategoryResponseDto;
import com.jr.sav.msvc.warehouse.entities.Category;
import com.jr.sav.msvc.warehouse.mapper.CategoryMapper;
import com.jr.sav.msvc.warehouse.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategortyServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final CodeGenerator codeGenerator;

    public CategortyServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CodeGenerator codeGenerator){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.codeGenerator = codeGenerator;
    }

    @Override    
    public CategoryResponseDto saveCtegory(CategoryRequestDto category){

        String prefix = category.getCategoryName().substring(0,2).toUpperCase();
        validateInfo(category.getCategoryName(), prefix);
        validateLength(category.getDescription());

        Category entity = categoryMapper.toEntity(category);
        entity.setCategoryPrefix(prefix);
        entity.setCategoryCode(codeGenerator.generateCategoryCode(entity));
        
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    @Override
    public List<CategoryResponseDto> fingAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                        .map(categoryMapper::toDto)
                        .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto findByCode(String code){
        Category category = findCategoryByCode(code);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto findByName(String name){
        Category category = categoryRepository.findByName(name)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro la categoria con el nombre "+ name));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto findByPrefix(String prefix){
        Category category = categoryRepository.findByPrefix(prefix)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro la categoria con el prefijo "+ prefix));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public void updateDescription(String code ,String description){
        Category category = findCategoryByCode(code);
        category.setDescription(description);
        categoryRepository.save(category);
    }

    private void validateInfo(String name, String prefix){
        if (categoryRepository.existCategoryByName(name)) {
            throw new IllegalArgumentException("La categoria "+ name + " ya se encuentra en el sitema");
        }else if (categoryRepository.existCategoryByPrefix(prefix)) {
            throw new IllegalArgumentException("El prefijo "+ prefix + " ya se encuentra en el sitema");
        }
    }

    private Category findCategoryByCode(String code){
        Category category = categoryRepository.findByCode(code)
                    .orElseThrow(() -> new EntityNotFoundException("No se econtro la categoria con el codigo "+ code));
        return category;
    }

    private void validateLength(String description){
        if (description.length() > 255) {
            throw new IllegalArgumentException("La descripcion de la categoria debe ser más corta");
        }
    }

}
