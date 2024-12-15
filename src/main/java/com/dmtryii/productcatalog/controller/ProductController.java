package com.dmtryii.productcatalog.controller;

import com.dmtryii.productcatalog.dto.ProductRequest;
import com.dmtryii.productcatalog.entity.Product;
import com.dmtryii.productcatalog.exceptions.ErrorResponse;
import com.dmtryii.productcatalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Tag(
        name = "Products",
        description = "Endpoints for managing products"
)
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping
    @Operation(
            summary = "Get all products", description = "Retrieve a paginated list of all products",
            tags = {"Products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products")
    })
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a product by ID", description = "Retrieve a single product by its ID",
            tags = {"Products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(
                    responseCode = "404", description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Product> getById(
            @Parameter(description = "ID of the product to retrieve", example = "1")
            @PathVariable Long id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(
            summary = "Create a new product", description = "Add a new product to the catalog",
            tags = {"Products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(
                    responseCode = "400", description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest request) {
        Product createdProduct = productService.create(
                map(request)
        );
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a product",
            description = "Update an existing product by its ID",
            tags = {"Products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(
                    responseCode = "400", description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Product> update(
            @Parameter(description = "ID of the product to update", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        Product updatedProduct = productService.update(id, map(request));
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a product", description = "Remove a product from the catalog by its ID",
            tags = {"Products"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID of the product to delete", example = "1")
            @PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    @Operation(
            summary = "Get products by category",
            description = "Retrieve a list of products filtered by category. If the category does not exist, an empty list will be returned."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
    })
    public List<Product> getByCategory(
            @Parameter(description = "Category of the products to retrieve", example = "Electronics", required = true)
            @PathVariable String category) {
        return productService.getByCategory(category);
    }

    private Product map(ProductRequest request) {
        return modelMapper.map(request, Product.class);
    }
}
