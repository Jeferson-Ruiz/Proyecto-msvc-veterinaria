package com.jr.sav.msvc.warehouse.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav.msvc.warehouse.dto.ProductDto;
import com.jr.sav.msvc.warehouse.entities.Product;
import com.jr.sav.msvc.warehouse.mapper.ProductMapper;
import com.jr.sav.msvc.warehouse.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

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

    @Override
    public Optional<ProductDto> saveProduct(ProductDto productDto){
        if (productRepository.findById(productDto.getId()).isPresent()) {
            return Optional.empty();
        }
        Product entity = productMapper.toEntity(productDto);
        entity.setRegistrationDate(LocalDate.now());
        return Optional.of(productMapper.toDto(productRepository.save(entity)));
    }


    @Override
    public void delete(Long id){
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Override    
    public void updateQuantityStock(Long id, Long quantityStock){
        if(productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        }
        productRepository.updateQuantityStock(id, quantityStock);
    }

    @Override
    public void updatePurchasePrice(Long id, Double purchasePrice){
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        }
        productRepository.updatePurchasePrice(id, purchasePrice);
    }

    @Override
    public void updateSalesPrice(long id, Double salesPrice){
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        }
        productRepository.updateSalesPrice(id, salesPrice);
    }

    



}
