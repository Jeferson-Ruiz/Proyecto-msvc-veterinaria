package com.jr.sav.msvc.warehouse.services;

import java.util.List;
import com.jr.sav.msvc.warehouse.dto.ProductRequestDto;
import com.jr.sav.msvc.warehouse.dto.ProductResponseDto;
import com.jr.sav.msvc.warehouse.entities.Comparison;
import com.jr.sav.msvc.warehouse.entities.ProductStatus;

public interface ProductService {
    ProductResponseDto saveProduct(ProductRequestDto productDto);

    List<ProductResponseDto> findAllByStatus(ProductStatus status);

    List<ProductResponseDto> findByStock(int stock, Comparison comparison);
    ProductResponseDto findByCode(String code);

    List<ProductResponseDto> findByName(String name);

    List<ProductResponseDto> findByCategory(String categoryName);

    void updatePrice(String codeProduct, Double newPrice);

    void updateStock(String codeProduct, int newStock);

    void updateStatus(String productCode, ProductStatus stados);

    void removeProduct(String codeProducto);

}
