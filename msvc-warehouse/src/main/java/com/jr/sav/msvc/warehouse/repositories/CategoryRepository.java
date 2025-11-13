package com.jr.sav.msvc.warehouse.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav.msvc.warehouse.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    @Query("SELECT c FROM Category c WHERE c.categoryCode =:categoryCode")
    Optional<Category> findByCode(@Param("categoryCode")String categoryCode);

    @Query("SELECT c FROM Category c WHERE c.categoryName =:categoryName")
    Optional<Category> findByName(@Param("categoryName") String categoryName);

    @Query("SELECT c FROM Category c WHERE c.categoryPrefix =:categoryPrefix")
    Optional<Category> findByPrefix(@Param("categoryPrefix") String categoryPrefix);

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.categoryName =:categoryName")
    Boolean existCategoryByName(@Param("categoryName") String categoryName);

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.categoryPrefix =:categoryPrefix")
    Boolean existCategoryByPrefix(@Param("categoryPrefix") String categoryPrefix);

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.categoryCode =:categoryCode")
    Boolean existCategoryByCode(@Param("categoryCode") String categoryCode);

}
