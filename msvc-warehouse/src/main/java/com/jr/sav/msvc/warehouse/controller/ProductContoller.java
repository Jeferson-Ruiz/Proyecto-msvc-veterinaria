package com.jr.sav.msvc.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav.msvc.warehouse.dto.ProductRequestDto;
import com.jr.sav.msvc.warehouse.dto.ProductResponseDto;
import com.jr.sav.msvc.warehouse.dto.StockRequestDto;
import com.jr.sav.msvc.warehouse.entities.ProductStatus;
import com.jr.sav.msvc.warehouse.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductContoller {

    private final ProductService productService;

    public ProductContoller(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> saveProducto(@RequestBody ProductRequestDto productDto){
        ProductResponseDto product = productService.saveProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllByStatus(@PathVariable ProductStatus status){
        return ResponseEntity.ok(productService.findAllByStatus(status));
    }

    @GetMapping("/stock")
    public ResponseEntity<?> getAllByStock(@RequestBody StockRequestDto request){
        return ResponseEntity.ok(productService.findByStock(request.getStock(),request.getComparison() ));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getByProductCode(@PathVariable String code){
        ProductResponseDto product = productService.findByCode(code);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getByCategoryName(@PathVariable String category){
        return ResponseEntity.ok(productService.findByCategory(category));
    }

    @PatchMapping("/update-price/{code}")
    public ResponseEntity<?> updatePriceByCode(@PathVariable String code, @RequestBody Double newPrice){
        productService.updatePrice(code, newPrice);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-stock/{code}")
    public ResponseEntity<?> updateStockByCode(@PathVariable String code, @RequestBody int stock){
        productService.updateStock(code, stock);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-statuc/{code}")
    public ResponseEntity<?> updateStatusByCode(@PathVariable String code, @RequestBody ProductStatus status){
        productService.updateStatus(code, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteProduct(@PathVariable String code){
        productService.removeProduct(code);
        return ResponseEntity.noContent().build();
    }

}
