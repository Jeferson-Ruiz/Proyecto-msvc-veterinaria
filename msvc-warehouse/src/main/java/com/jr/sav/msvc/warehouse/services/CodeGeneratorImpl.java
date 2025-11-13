package com.jr.sav.msvc.warehouse.services;


import java.security.SecureRandom;
import org.springframework.stereotype.Service;
import com.jr.sav.msvc.warehouse.entities.Category;
import com.jr.sav.msvc.warehouse.entities.Product;
import com.jr.sav.msvc.warehouse.repositories.CategoryRepository;
import com.jr.sav.msvc.warehouse.repositories.ProductRepository;

@Service
public class CodeGeneratorImpl implements CodeGenerator {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final SecureRandom random = new SecureRandom();

    public CodeGeneratorImpl(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String generateProductoCode(Product product){
        // Prefijo de la categoría
        String categoryPrefix = product.getCategory().getCategoryPrefix().toUpperCase();

        // Prefijo del subgrupo (primeras dos letras)
        String subgroupPrefix = product.getSubgroup()
                .trim()
                .substring(0, Math.min(2, product.getSubgroup().length()))
                .toUpperCase();

        // Contar productos existentes en esa categoría + subgrupo
        long count = productRepository.countByCategoryAndSubgroup(
                product.getCategory(), product.getSubgroup());

        // Generar número consecutivo (sumar 1 al conteo actual)
        long nextNumber = count + 1;
    
        // Formatear número con ceros a la izquierda (4 dígitos)
        String formattedNumber = String.format("%04d", nextNumber);

        return String.format("%s-%s-%s", categoryPrefix, subgroupPrefix, formattedNumber);
    }

    @Override
    public String generateCategoryCode(Category category){
        String code;
        boolean exist;

        do {
            //Crea un numero random
            int numero = 1000 + random.nextInt(9000);
            code = category.getCategoryPrefix() +"-"+numero;

            //Valida si no existe ese codigo en el sistema
            exist = categoryRepository.existCategoryByCode(code);
        } while (exist);
        
        return code;
    }



}
