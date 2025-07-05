package com.jr.sav.msvc.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jr.sav.msvc.warehouse.entities.Product;

public interface MedicinesRepository extends JpaRepository<Product, Long>{

}
