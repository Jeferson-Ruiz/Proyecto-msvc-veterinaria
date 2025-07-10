package com.jr.sav.msvc.warehouse.services;

import java.util.List;
import java.util.Optional;
import com.jr.sav.msvc.warehouse.dto.ProductDto;

public interface ProductService {
    List<ProductDto> getAllProducts();

    Optional<ProductDto> findById(Long id);

    Optional<ProductDto> saveProduct(ProductDto productDto);

    void delete(Long id);

    

}
