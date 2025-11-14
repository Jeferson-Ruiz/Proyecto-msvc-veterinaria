package com.jr.sav.msvc.warehouse.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jr.sav.msvc.warehouse.dto.ProductRequestDto;
import com.jr.sav.msvc.warehouse.dto.ProductResponseDto;
import com.jr.sav.msvc.warehouse.entities.Category;
import com.jr.sav.msvc.warehouse.entities.Comparison;
import com.jr.sav.msvc.warehouse.entities.Product;
import com.jr.sav.msvc.warehouse.entities.ProductStatus;
import com.jr.sav.msvc.warehouse.mapper.ProductMapper;
import com.jr.sav.msvc.warehouse.repositories.CategoryRepository;
import com.jr.sav.msvc.warehouse.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final CodeGenerator codeGenerator;

    ProductServiceImpl(ProductRepository productRepository,
            ProductMapper productMapper,
            CodeGenerator codeGenerator,
            CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.codeGenerator = codeGenerator;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ProductResponseDto saveProduct(ProductRequestDto productDto){
        validateExistence(productDto.getProductName(), productDto.getProductBrand());

        //Evaluar si la catergoria que se asigna al producto existe
        String categoryProduco = productDto.getCategoryCode();
        Category category = findByCategoryCode(categoryProduco);
        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);

        String code = codeGenerator.generateProductoCode(product);
        product.setProductCode(code);

        product.setStatus(ProductStatus.AVAILABLE);
        product.setRegistrationDate(LocalDateTime.now());

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDto> findAllByStatus(ProductStatus status){
        List<Product> products = productRepository.findAllByStatus(status);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron productos con el estado "+status);
        }
        return returnToDtoList(products);
    }

    //buscar por mayor que o menor 
    @Override
    public List<ProductResponseDto> findByStock(int stock, Comparison comparison){
       
        List<Product> products = switch (comparison) {
            case EQUAL   -> productRepository.findAllByStock(stock);
            case GREATER -> productRepository.findAllByStockGreaterThan(stock);
            case LESS    -> productRepository.findAllByStockLessThan(stock);
        };

        return returnToDtoList(products);
    }

    @Override
    public ProductResponseDto findByCode(String code){
        Product product = findProductByCode(code);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductResponseDto> findByName(String name){
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No se econtro producto "+ name);
        }
        return returnToDtoList(products);
    }

    @Override
    public List<ProductResponseDto> findByCategory(String categoryName){
        List<Product> products = productRepository.findAllByCategoryName(categoryName);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran productos en la categoria" + categoryName);
        }
        return returnToDtoList(products);
    }

    @Override
    @Transactional
    public void updatePrice(String codeProduct, Double newPrice){
        validateQuality(newPrice);

        Product product = findProductByCode(codeProduct);
        product.setSalesPrice(newPrice);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateStock(String codeProduct, int newStock){
        validateQuality((double) newStock);

        Product product = findProductByCode(codeProduct);

        if (newStock == product.getQuantityStock()) {
            throw new IllegalArgumentException("Error, el producto ya cuanta con esa cantidad en stock");}

        product.setQuantityStock(newStock);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateStatus(String productCode, ProductStatus status){

        if (status.equals(ProductStatus.DELETED)) {
            throw new IllegalArgumentException("No se puede eliminar un productos desde este panel");}

        Product product = findProductByCode(productCode);

        if (product.getStatus().equals(ProductStatus.DELETED)) {
            throw new IllegalArgumentException("No se puede eliminar actualizar un producto eliminado del sistema");
        }
        else if(product.getQuantityStock() < 1) {
            product.setStatus(ProductStatus.SOLD_OUT);

        }else if (status.equals(ProductStatus.AVAILABLE) && product.getQuantityStock() < 1) {
            throw new IllegalArgumentException("El producto no puede estar disponible un producto sin stock");
        }else{
            product.setStatus(status);
        }
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void removeProduct(String codeProducto){
        Product producto = findProductByCode(codeProducto);
        if (producto.getStatus().equals(ProductStatus.DELETED)) {
            throw new IllegalArgumentException("El producto ya se encuentra eliminado del sistema");}
        producto.setStatus(ProductStatus.DELETED);
        productRepository.save(producto);
    }
    
    //helpers
    private Product findProductByCode(String code){
        Product product = productRepository.findByCode(code)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro producto asociado al codigo " + code));
        return product;
    }
    
    private void validateExistence(String name, String brand){
        if (productRepository.existeProductByNameAndBrand(name, brand)) {
            throw new IllegalArgumentException("El producto ya existe en el sistema");
        }
    } 

    private void validateQuality (Double cantidad){
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    }

    private Category findByCategoryCode(String code){
        Category category = categoryRepository.findByCode(code)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro categoria para el porducto"));
        return category;
    }

    private List<ProductResponseDto> returnToDtoList(List<Product> products) {
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }


}
