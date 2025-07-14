package com.jr.sav.msvc.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav.msvc.warehouse.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("update Product set quantityStock=:quantityStock where id=:id")
    void updateQuantityStock(@Param("id") Long id, @Param("quantityStock") Long quantityStock);

    @Modifying
    @Query("update Product set purchasePrice=:purchasePrice WHERE id=:id")
    void updatePurchasePrice(@Param("id") Long id, @Param("purchasePrice") Double purchasePrice);

    @Modifying
    @Query("update Product set salesPrice=:salesPrice Where id=:id")
    void updateSalesPrice(@Param("id") Long id, @Param("salesPrice") Double salesPrice);
}
