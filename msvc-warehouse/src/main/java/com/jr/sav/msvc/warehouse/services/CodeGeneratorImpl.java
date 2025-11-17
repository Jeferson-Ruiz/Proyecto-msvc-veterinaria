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
    
    @Override
    public String generarPrefijo(String texto, CategoryRepository categoryRepository) {
        String prefijoBase = generarPrefijoBase(texto);
        return generarPrefijoUnico(prefijoBase, categoryRepository);
    }

    private String generarPrefijoBase(String texto) {
        texto = texto.strip().replaceAll("\\s+", " ").toUpperCase();

        String[] palabras = texto.split(" ");
        StringBuilder prefijo = new StringBuilder();

        for (String p : palabras) {
            if (p.length() >= 2) {
                prefijo.append(p.substring(0, 2));
            } else {
                prefijo.append(p);
            }
        }
        // Limitar a 6 el prefijo
        if (prefijo.length() >= 6) {
            return prefijo.substring(0, 6);
        }

        return prefijo.toString();
    }


    private String generarPrefijoUnico(String prefijoBase, CategoryRepository categoryRepository) {
        String prefijo = prefijoBase;
        int contador = 1;
        
        while (categoryRepository.existCategoryByPrefix(prefijo)) {
            String sufijo = String.valueOf(contador);
            int longitudMaxima = 6 - sufijo.length();
            
            if (longitudMaxima <= 0) {
                prefijo = sufijo.substring(0, 6);
            } else {
                // Asegurar que no exceda la longitud del prefijoBase
                int longitudRecortar = Math.min(prefijoBase.length(), longitudMaxima);
                prefijo = prefijoBase.substring(0, longitudRecortar) + sufijo;
            }
            contador++;
        }
        
        return prefijo;
    }
}
