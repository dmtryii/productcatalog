package com.dmtryii.productcatalog.service.impl;

import com.dmtryii.productcatalog.entity.Product;
import com.dmtryii.productcatalog.exceptions.EntityNotFoundException;
import com.dmtryii.productcatalog.repository.ProductRepository;
import com.dmtryii.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "#id")
    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found by id " + id)
        );
    }

    @Cacheable(value = "productsByCategory", key = "#category")
    @Override
    public List<Product> getByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    @Override
    public Product update(Long id, Product updatedProduct) {
        Product existingProduct = getById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setStock(updatedProduct.getStock());
        return productRepository.save(existingProduct);
    }

    @CacheEvict(value = {"products", "productsByCategory"}, allEntries = true)
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
