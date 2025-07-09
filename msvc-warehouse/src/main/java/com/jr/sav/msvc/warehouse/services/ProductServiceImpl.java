package com.jr.sav.msvc.warehouse.services;

import java.util.List;
import com.jr.sav.msvc.warehouse.dto.ProductDto;
import com.jr.sav.msvc.warehouse.mapper.ProductMapper;
import com.jr.sav.msvc.warehouse.repositories.ProductRepository;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    public List<ProductDto> getProducts(){
        return productRepository.findAll().stream()
            .map(productMapper::toDto).toList();
    }
    




}
