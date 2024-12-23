package com.dmtryii.productcatalog;

import com.dmtryii.productcatalog.entity.Product;
import com.dmtryii.productcatalog.exceptions.EntityNotFoundException;
import com.dmtryii.productcatalog.repository.ProductRepository;
import com.dmtryii.productcatalog.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceBaseLogicTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Description")
                .price(BigDecimal.valueOf(99.99))
                .category("Category1")
                .build();
    }

    @Test
    public void testGetById_ProductFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getById(1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
    }

    @Test
    public void testGetById_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> productService.getById(1L)
        );
        assertEquals("Product not found by id 1", exception.getMessage());
    }

    @Test
    public void testGetByCategory_ProductsFound() {
        when(productRepository.findByCategoryIgnoreCase("Category")).thenReturn(List.of(product));

        List<Product> result = productService.getByCategory("Category");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product.getCategory(), result.get(0).getCategory());
    }

    @Test
    void testGetAll_ProductsFound() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> result = productService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testCreate_ProductCreated() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
    }

    @Test
    void testUpdate_ProductUpdated() {
        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .price(BigDecimal.valueOf(120.0))
                .category("Category")
                .stock(60)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.update(1L, updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getDescription(), result.getDescription());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
    }

    @Test
    public void testDeleteById_ProductDeleted() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
