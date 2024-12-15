package com.dmtryii.productcatalog.service;

import com.dmtryii.productcatalog.entity.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long id);
    List<Product> getByCategory(String category);
    List<Product> getAll();
    Product create(Product product);
    Product update(Long id, Product updatedProduct);
    void deleteById(Long id);
}
