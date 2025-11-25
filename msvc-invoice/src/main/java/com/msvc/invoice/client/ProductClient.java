package com.msvc.invoice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.msvc.invoice.dto.ProductClientDto;

@FeignClient(name = "msvc-warehouse")
public interface ProductClient {

    @GetMapping("product/code/{code}")
    ProductClientDto getByProductCode(@PathVariable ("code") String code);

    @GetMapping("product/name")
    ProductClientDto getByName(@RequestParam String name);
    
    @PutMapping("product/update-stock/{code}")
    void updateStockByCode(@PathVariable String code ,@RequestParam int stock);

}
