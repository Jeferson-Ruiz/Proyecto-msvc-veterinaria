package com.jr.sav.msvc.warehouse.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav.msvc.warehouse.dto.ProductDto;
import com.jr.sav.msvc.warehouse.entities.Product;
import com.jr.sav.msvc.warehouse.mapper.ProductMapper;
import com.jr.sav.msvc.warehouse.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> getAllProducts(){
        return productRepository.findAll().stream()
            .map(productMapper::toDto).toList();
    }

    @Override
    public Optional<ProductDto> findById(Long id){
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isEmpty()) {
            return Optional.empty();
        }
        ProductDto dto = productMapper.toDto(optProduct.get());
        return Optional.of(dto);
    }



}
