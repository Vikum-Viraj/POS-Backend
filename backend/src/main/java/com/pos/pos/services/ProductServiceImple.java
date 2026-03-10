package com.pos.pos.services;

import com.pos.pos.entity.ProductEntity;
import com.pos.pos.model.Product;
import com.pos.pos.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImple implements ProductService {

    private final ProductRepository productRepository;
    public ProductServiceImple(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCreatedDate(LocalDateTime.now());
        productEntity.setCode(product.getCode());
        productEntity.setCost(product.getCost());
        productEntity.setName(product.getName());
        productEntity.setUnit(product.getUnit());
        productEntity.setMrp(product.getMrp());
        productEntity.setQuantity(product.getQuantity());

        ProductEntity savedEntity = productRepository.save(productEntity);

        Product savedProduct = new Product();
        savedProduct.setId(savedEntity.getId());
        savedProduct.setCode(savedEntity.getCode());
        savedProduct.setUnit(savedEntity.getUnit());
        savedProduct.setCost(savedEntity.getCost());
        savedProduct.setName(savedEntity.getName());
        savedProduct.setMrp(savedEntity.getMrp());
        savedProduct.setQuantity(savedEntity.getQuantity());

        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(entity -> {
                    Product product = new Product();
                    BeanUtils.copyProperties(entity, product);
                    return product;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isPresent()) {
            Product product = new Product();
            BeanUtils.copyProperties(productEntity.get(), product);
            return product;
        }
        return null; // or throw exception
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        ProductEntity existingEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingEntity.setName(product.getName());
        existingEntity.setUpdatedDate(LocalDateTime.now());
        existingEntity.setCode(product.getCode());
        existingEntity.setUnit(product.getUnit());
        existingEntity.setCost(product.getCost());
        existingEntity.setMrp(product.getMrp());
        existingEntity.setQuantity(product.getQuantity());

        ProductEntity updatedEntity = productRepository.save(existingEntity);
        Product updatedProduct = new Product();
        BeanUtils.copyProperties(updatedEntity, updatedProduct);
        return updatedProduct;
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
