package com.jr.sav.msvc.warehouse.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav.msvc.warehouse.entities.Category;
import com.jr.sav.msvc.warehouse.entities.Product;
import com.jr.sav.msvc.warehouse.entities.ProductStatus;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.productCode =:productCode")
    Optional<Product> findByCode(@Param("productCode") String productCode);

    @Query("SELECT p FROM Product p WHERE p.productName =:productName")
    List<Product> findByName(@Param("productName") String productName);

    //Stock
    @Query("SELECT p FROM Product p WHERE p.quantityStock =:quantityStock")
    List<Product> findAllByStock(@Param("quantityStock") int quantityStock);

    @Query("SELECT p FROM Product p WHERE p.quantityStock >:quantityStock")
    List<Product> findAllByStockGreaterThan(@Param("quantityStock") int quantityStock);

    @Query("SELECT p FROM Product p WHERE p.quantityStock <:quantityStock")
    List<Product> findAllByStockLessThan(@Param("quantityStock") int quantityStock);

    @Query("SELECT p FROM Product p WHERE p.category.categoryName =:name")
    List<Product> findAllByCategoryName(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.status =:status")
    List<Product> findAllByStatus(@Param("status") ProductStatus status);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.productName = :productName AND p.productBrand =:productBrand")
    Boolean existeProductByNameAndBrand(@Param("productName") String productName, @Param("productBrand") String productBrand);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category = :category AND p.subgroup = :subgroup")
    long countByCategoryAndSubgroup(@Param("category") Category category, @Param("subgroup") String subgroup);

}
