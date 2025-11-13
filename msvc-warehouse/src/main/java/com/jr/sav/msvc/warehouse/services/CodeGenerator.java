package com.jr.sav.msvc.warehouse.services;

import com.jr.sav.msvc.warehouse.entities.Category;
import com.jr.sav.msvc.warehouse.entities.Product;

public interface CodeGenerator {

    String generateProductoCode(Product product);
    
    String generateCategoryCode(Category category);
}
