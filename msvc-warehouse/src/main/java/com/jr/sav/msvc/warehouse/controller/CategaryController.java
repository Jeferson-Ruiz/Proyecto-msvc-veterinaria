package com.jr.sav.msvc.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav.msvc.warehouse.dto.CategoryRequestDto;
import com.jr.sav.msvc.warehouse.dto.CategoryResponseDto;
import com.jr.sav.msvc.warehouse.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategaryController {

    private final CategoryService categoryService;

    public CategaryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategoryRequestDto category){
        CategoryResponseDto categoryDto = categoryService.saveCtegory(category);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryService.fingAllCategory());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable String code){
        CategoryResponseDto category = categoryService.findByCode(code);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        CategoryResponseDto category = categoryService.findByName(name);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/prefix/{prefix}")
    public ResponseEntity<?> getByPrefix(@PathVariable String prefix){
        CategoryResponseDto category = categoryService.findByPrefix(prefix);
        return ResponseEntity.ok(category);
    }

    @PatchMapping("/update-description/{code}")
    public ResponseEntity<?> updateDescription(@PathVariable String code, @RequestBody String prefix){
        categoryService.updateDescription(code, prefix);
        return ResponseEntity.noContent().build();
    }
}
